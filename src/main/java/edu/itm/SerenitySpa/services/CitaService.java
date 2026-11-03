package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.dto.CitaRequest;
import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.exceptions.ReglaNegocioException;
import edu.itm.SerenitySpa.identities.Cita;
import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.identities.Sede;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.ICitaRepository;
import edu.itm.SerenitySpa.repositories.IClienteRepository;
import edu.itm.SerenitySpa.repositories.IEmpleadoRepository;
import edu.itm.SerenitySpa.repositories.ISedeRepository;
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
    private final ISedeRepository sedeRepository;

    public CitaService(
            ICitaRepository citaRepository,
            IClienteRepository clienteRepository,
            IServicioRepository servicioRepository,
            IEmpleadoRepository empleadoRepository,
            ISedeRepository sedeRepository) {

        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.empleadoRepository = empleadoRepository;
        this.sedeRepository = sedeRepository;
    }

    @Override
    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    @Override
    public Cita buscar(int id) {
        return citaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró la cita con ID: " + id
                        ));
    }

    @Override
    public Cita agendar(CitaRequest cita) {

        if (cita == null) {
            throw new IllegalArgumentException(
                    "Los datos de la cita son obligatorios."
            );
        }

        if (cita.getFecha() == null
                || cita.getHoraInicio() == null) {
            throw new IllegalArgumentException(
                    "La fecha y la hora de inicio son obligatorias."
            );
        }

        // Validar que la fecha no sea pasada
        if (cita.getFecha().isBefore(java.time.LocalDate.now())) {
            throw new ReglaNegocioException(
                    "No se pueden agendar citas en fechas pasadas."
            );
        }

// Horario de atención: 8:00 a. m. a 6:00 p. m.
        LocalTime apertura = LocalTime.of(8, 0);
        LocalTime cierre = LocalTime.of(18, 0);

// Validar hora de inicio
        if (cita.getHoraInicio().isBefore(apertura)
                || !cita.getHoraInicio().isBefore(cierre)) {
            throw new ReglaNegocioException(
                    "El horario de atención es de 8:00 a. m. a 6:00 p. m."
            );
        }

        if (cita.getIdCliente() == null || cita.getIdCliente() <= 0
                || cita.getIdServicio() == null || cita.getIdServicio() <= 0
                || cita.getIdEmpleado() == null || cita.getIdEmpleado() <= 0
                || cita.getIdSede() == null || cita.getIdSede() <= 0) {
            throw new IllegalArgumentException(
                    "Los identificadores del cliente, servicio, empleado y sede son obligatorios."
            );
        }

        Cliente cliente = clienteRepository.findById(cita.getIdCliente())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el cliente."
                        ));

        Servicio servicio = servicioRepository.findById(cita.getIdServicio())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el servicio."
                        ));

        Empleado empleado = empleadoRepository.findById(cita.getIdEmpleado())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el empleado."
                        ));

        Sede sede = sedeRepository.findById(cita.getIdSede())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró la sede."
                        ));

        if (servicio.getDuracion() <= 0) {
            throw new ReglaNegocioException(
                    "La duración del servicio debe ser mayor a cero."
            );
        }

        boolean empleadoHabilitado = empleado.getServiciosHabilitados()
                .stream()
                .anyMatch(s -> s.getIdServicio() == servicio.getIdServicio());

        if (!empleadoHabilitado) {
            throw new ReglaNegocioException(
                    "El empleado no está habilitado para realizar este servicio."
            );
        }

        Cita nuevaCita = new Cita();

        nuevaCita.setFecha(cita.getFecha());
        nuevaCita.setHoraInicio(cita.getHoraInicio());
        nuevaCita.setHoraFin(
                cita.getHoraInicio().plusMinutes(servicio.getDuracion())
        );
        if (!nuevaCita.getHoraFin().isAfter(cita.getHoraInicio())) {
            throw new ReglaNegocioException(
                    "El horario de finalización no es válido."
            );
        }
        if (nuevaCita.getHoraFin().isAfter(cierre)) {
            throw new ReglaNegocioException(
                    "La cita no puede terminar después de las 6:00 p. m."
            );
        }

        nuevaCita.setEstado("AGENDADA");
        nuevaCita.setCliente(cliente);
        nuevaCita.setServicio(servicio);
        nuevaCita.setEmpleado(empleado);
        nuevaCita.setSede(sede);

        validarDisponibilidadEmpleado(nuevaCita, 0);
        validarDisponibilidadCliente(nuevaCita, 0);

        return citaRepository.save(nuevaCita);
    }

    @Override
    public Cita actualizar(int id, CitaRequest cita) {
        Cita existente = buscar(id);

        if (cita == null) {
            throw new IllegalArgumentException("Los datos de actualización son obligatorios.");
        }

        if (cita.getFecha() == null || cita.getHoraInicio() == null) {
            throw new IllegalArgumentException("La fecha y la hora de inicio son obligatorias.");
        }

        if (cita.getFecha().isBefore(java.time.LocalDate.now())) {
            throw new ReglaNegocioException("No se pueden agendar citas en fechas pasadas.");
        }

        if (cita.getIdCliente() == null || cita.getIdCliente() <= 0 ||
                cita.getIdServicio() == null || cita.getIdServicio() <= 0 ||
                cita.getIdEmpleado() == null || cita.getIdEmpleado() <= 0 ||
                cita.getIdSede() == null || cita.getIdSede() <= 0) {
            throw new IllegalArgumentException("Los identificadores del cliente, servicio, empleado y sede son obligatorios.");
        }

        LocalTime apertura = LocalTime.of(8, 0);
        LocalTime cierre = LocalTime.of(18, 0);

        if (cita.getHoraInicio().isBefore(apertura) ||
                !cita.getHoraInicio().isBefore(cierre)) {
            throw new ReglaNegocioException("El horario de atención es de 8:00 a. m. a 6:00 p. m.");
        }

        Cliente cliente = clienteRepository.findById(cita.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente."));

        Servicio servicio = servicioRepository.findById(cita.getIdServicio())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el servicio."));

        Empleado empleado = empleadoRepository.findById(cita.getIdEmpleado())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el empleado."));

        Sede sede = sedeRepository.findById(cita.getIdSede())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la sede."));

        if (servicio.getDuracion() <= 0) {
            throw new ReglaNegocioException("La duración del servicio debe ser mayor a cero.");
        }

        boolean empleadoHabilitado = empleado.getServiciosHabilitados() != null
                && empleado.getServiciosHabilitados().stream()
                .anyMatch(s -> s.getIdServicio() == servicio.getIdServicio());

        if (!empleadoHabilitado) {
            throw new ReglaNegocioException("El empleado no está habilitado para realizar este servicio.");
        }

        LocalTime horaFin = cita.getHoraInicio().plusMinutes(servicio.getDuracion());

        if (!horaFin.isAfter(cita.getHoraInicio())) {
            throw new ReglaNegocioException("El horario de finalización no es válido.");
        }

        if (horaFin.isAfter(cierre)) {
            throw new ReglaNegocioException("La cita no puede terminar después de las 6:00 p. m.");
        }

        existente.setFecha(cita.getFecha());
        existente.setHoraInicio(cita.getHoraInicio());
        existente.setHoraFin(horaFin);
        existente.setCliente(cliente);
        existente.setServicio(servicio);
        existente.setEmpleado(empleado);
        existente.setSede(sede);

        if (cita.getEstado() != null && !cita.getEstado().isBlank()) {
            validarEstado(cita.getEstado());
            existente.setEstado(cita.getEstado().trim().toUpperCase());
        }

        if (!"CANCELADA".equalsIgnoreCase(existente.getEstado())) {
            validarDisponibilidadEmpleado(existente, id);
            validarDisponibilidadCliente(existente, id);
        }

        return citaRepository.save(existente);
    }

    private void validarEstado(String estado) {
        String estadoNormalizado = estado.trim().toUpperCase();

        if (!List.of("AGENDADA", "CANCELADA", "COMPLETADA")
                .contains(estadoNormalizado)) {
            throw new ReglaNegocioException(
                    "El estado debe ser AGENDADA, CANCELADA o COMPLETADA."
            );
        }
    }
    private void validarDisponibilidadEmpleado(Cita cita, int idExcluir) {

        List<Cita> citas = citaRepository
                .findByEmpleadoIdEmpleadoAndFecha(
                        cita.getEmpleado().getIdEmpleado(),
                        cita.getFecha()
                );

        for (Cita otra : citas) {

            if (otra.getIdCita() == idExcluir
                    || "CANCELADA".equalsIgnoreCase(otra.getEstado())) {
                continue;
            }

            boolean seCruzan =
                    cita.getHoraInicio().isBefore(otra.getHoraFin())
                            && cita.getHoraFin().isAfter(otra.getHoraInicio());

            if (seCruzan) {
                throw new ReglaNegocioException(
                        "El empleado ya tiene una cita en ese horario."
                );
            }
        }
    }

    private void validarDisponibilidadCliente(Cita cita, int idExcluir) {

        List<Cita> citas = citaRepository
                .findByClienteIdClienteAndFecha(
                        cita.getCliente().getIdCliente(),
                        cita.getFecha()
                );

        for (Cita otra : citas) {

            if (otra.getIdCita() == idExcluir
                    || "CANCELADA".equalsIgnoreCase(otra.getEstado())) {
                continue;
            }

            boolean seCruzan =
                    cita.getHoraInicio().isBefore(otra.getHoraFin())
                            && cita.getHoraFin().isAfter(otra.getHoraInicio());

            if (seCruzan) {
                throw new ReglaNegocioException(
                        "El cliente ya tiene una cita en ese horario."
                );
            }
        }
    }

    @Override
    public void cancelar(int id) {

        Cita cita = buscar(id);

        if ("CANCELADA".equalsIgnoreCase(cita.getEstado())) {
            throw new ReglaNegocioException(
                    "La cita ya se encuentra cancelada."
            );
        }

        if ("COMPLETADA".equalsIgnoreCase(cita.getEstado())) {
            throw new ReglaNegocioException(
                    "No se puede cancelar una cita que ya está completada."
            );
        }

        cita.setEstado("CANCELADA");

        citaRepository.save(cita);
    }
}