package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.exceptions.ServicioEnUsoException;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.IServicioRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioService implements IServicioService {

    private final IServicioRepository servicioRepository;

    public ServicioService(IServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio buscar(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el servicio con ID: " + id
                        ));
    }

    @Override
    public Servicio guardar(Servicio servicio) {

        validarServicio(servicio);

        servicio.setIdServicio(0);

        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizar(int id, Servicio servicio) {

        Servicio existente = buscar(id);

        validarServicio(servicio);

        existente.setNombre(servicio.getNombre());
        existente.setDescripcion(servicio.getDescripcion());
        existente.setDuracion(servicio.getDuracion());
        existente.setPrecio(servicio.getPrecio());

        return servicioRepository.save(existente);
    }

    @Override
    @Transactional
    public boolean eliminar(int id) {

        Servicio servicio = buscar(id);

        try {
            servicioRepository.delete(servicio);
            servicioRepository.flush();
            return true;

        } catch (DataIntegrityViolationException ex) {
            throw new ServicioEnUsoException(
                    "No se puede eliminar el servicio porque está relacionado con otros registros.",
                    ex
            );
        }
    }

    private void validarServicio(Servicio servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "Los datos del servicio son obligatorios."
            );
        }

        if (servicio.getNombre() == null
                || servicio.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del servicio es obligatorio."
            );
        }

        if (servicio.getDuracion() <= 0) {
            throw new IllegalArgumentException(
                    "La duración debe ser mayor que cero."
            );
        }

        if (servicio.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }
    }
}
