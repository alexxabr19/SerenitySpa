package edu.itm.SerenitySpa.services;

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
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        servicio.setIdServicio(0);
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizar(int id, Servicio servicio) {
        Servicio existente = buscar(id);
        existente.setNombre(servicio.getNombre());
        existente.setDescripcion(servicio.getDescripcion());
        existente.setDuracion(servicio.getDuracion());
        existente.setPrecio(servicio.getPrecio());
        return servicioRepository.save(existente);
    }

    @Override
    public boolean eliminar(int id) {
        Servicio servicio = buscar(id);
        servicioRepository.flush();
        servicioRepository.delete(servicio);
        return false;
    }
}
