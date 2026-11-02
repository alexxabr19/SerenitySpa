
package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.IEmpleadoRepository;
import edu.itm.SerenitySpa.repositories.IServicioRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService implements IEmpleadoService {

    private final IEmpleadoRepository empleadoRepository;
    private final IServicioRepository servicioRepository;

    public EmpleadoService(
            IEmpleadoRepository empleadoRepository,
            IServicioRepository servicioRepository) {

        this.empleadoRepository = empleadoRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscar(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el empleado con ID: " + id
                        ));
    }

    @Override
    public Empleado guardar(Empleado empleado) {

        validarEmpleado(empleado);

        empleado.setIdEmpleado(0);

        empleado.setServiciosHabilitados(
                obtenerServicios(empleado.getServiciosHabilitados())
        );

        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizar(int id, Empleado empleado) {

        Empleado existente = buscar(id);

        validarEmpleado(empleado);

        existente.setNombre(empleado.getNombre());
        existente.setCargo(empleado.getCargo());
        existente.setCorreo(empleado.getCorreo());
        existente.setTelefono(empleado.getTelefono());

        existente.setServiciosHabilitados(
                obtenerServicios(empleado.getServiciosHabilitados())
        );

        return empleadoRepository.save(existente);
    }

    @Override
    public void eliminar(int id) {

        Empleado empleado = buscar(id);

        empleadoRepository.delete(empleado);
    }

    private List<Servicio> obtenerServicios(List<Servicio> servicios) {

        if (servicios == null || servicios.isEmpty()) {
            return new ArrayList<>();
        }

        return servicios.stream()
                .map(servicio -> servicioRepository
                        .findById(servicio.getIdServicio())
                        .orElseThrow(() ->
                                new RecursoNoEncontradoException(
                                        "No se encontró el servicio con ID: "
                                                + servicio.getIdServicio()
                                )))
                .collect(Collectors.toList());
    }

    private void validarEmpleado(Empleado empleado) {

        if (empleado == null) {
            throw new IllegalArgumentException(
                    "Los datos del empleado son obligatorios."
            );
        }

        if (empleado.getNombre() == null
                || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del empleado es obligatorio."
            );
        }

        if (empleado.getCorreo() == null
                || empleado.getCorreo().isBlank()) {
            throw new IllegalArgumentException(
                    "El correo del empleado es obligatorio."
            );
        }
    }
}
