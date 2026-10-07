package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cita;
import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.ICitaRepository;
import edu.itm.SerenitySpa.repositories.IClienteRepository;
import edu.itm.SerenitySpa.repositories.IEmpleadoRepository;
import edu.itm.SerenitySpa.repositories.IServicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService implements ICitaService {

    private final ICitaRepository citaRepository;
    private final IClienteRepository clienteRepository;
    private final IServicioRepository servicioRepository;
    private final IEmpleadoRepository empleadoRepository;

    public CitaService(
            ICitaRepository citaRepository,
            IClienteRepository clienteRepository,
            IServicioRepository servicioRepository,
            IEmpleadoRepository empleadoRepository) {

        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    @Override
    public Cita buscar(int id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    @Override
    public Cita agendar(Cita cita) {

        if (!clienteRepository.existsById(
                cita.getCliente().getIdCliente())) {

            throw new RuntimeException("El cliente no existe");
        }

        Servicio servicio = servicioRepository.findById(
                        cita.getServicio().getIdServicio())
                .orElseThrow(() ->
                        new RuntimeException("El servicio no existe"));

        Empleado empleado = empleadoRepository.findById(
                        cita.getEmpleado().getIdEmpleado())
                .orElseThrow(() ->
                        new RuntimeException("El empleado no existe"));

        boolean empleadoHabilitado =
                empleado.getServiciosHabilitados()
                        .stream()
                        .anyMatch(s ->
                                s.getIdServicio() ==
                                        servicio.getIdServicio());

        if (!empleadoHabilitado) {
            throw new RuntimeException(
                    "El empleado no está habilitado para este servicio");
        }

        LocalTime horaFin = cita.getHoraInicio()
                .plusMinutes(servicio.getDuracion());

        cita.setHoraFin(horaFin);
        cita.setEstado("AGENDADA");

        validarDisponibilidadEmpleado(cita);
        validarDisponibilidadCliente(cita);

        return citaRepository.save(cita);
    }

    private void validarDisponibilidadEmpleado(Cita cita) {

        List<Cita> citasEmpleado =
                citaRepository.findByEmpleadoIdEmpleadoAndFecha(
                        cita.getEmpleado().getIdEmpleado(),
                        cita.getFecha());

        for (Cita existente : citasEmpleado) {

            boolean seCruzan =
                    cita.getHoraInicio().isBefore(existente.getHoraFin())
                            && cita.getHoraFin().isAfter(existente.getHoraInicio());

            if (seCruzan) {
                throw new RuntimeException(
                        "El empleado ya tiene una cita en ese horario");
            }
        }
    }

    private void validarDisponibilidadCliente(Cita cita) {

        List<Cita> citasCliente =
                citaRepository.findByClienteIdClienteAndFecha(
                        cita.getCliente().getIdCliente(),
                        cita.getFecha());

        for (Cita existente : citasCliente) {

            boolean seCruzan =
                    cita.getHoraInicio().isBefore(existente.getHoraFin())
                            && cita.getHoraFin().isAfter(existente.getHoraInicio());

            if (seCruzan) {
                throw new RuntimeException(
                        "El cliente ya tiene una cita en ese horario");
            }
        }
    }

    @Override
    public Cita actualizar(int id, Cita cita) {

        Cita existente = buscar(id);

        existente.setFecha(cita.getFecha());
        existente.setHoraInicio(cita.getHoraInicio());
        existente.setHoraFin(cita.getHoraFin());
        existente.setEstado(cita.getEstado());

        return citaRepository.save(existente);
    }

    @Override
    public void cancelar(int id) {

        Cita cita = buscar(id);

        cita.setEstado("CANCELADA");

        citaRepository.save(cita);
    }
}
