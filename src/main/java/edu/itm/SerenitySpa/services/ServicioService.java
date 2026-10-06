package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    public List<Servicio> listar() {
        return servicioRepository.listarServicios();
    }

    @Override
    public Servicio buscar(int id) {
        return servicioRepository.buscarServicio(id);
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        return servicioRepository.insertarServicio(servicio);
    }

    @Override
    public Servicio actualizar(int id, Servicio servicio) {
        servicio.setIdServicio(id); // el id viene de la URL
        boolean actualizado = servicioRepository.actualizarServicio(servicio);
        return actualizado ? servicio : null;
    }

    @Override
    public boolean eliminar(int id) {
        return servicioRepository.eliminarServicio(id);
    }
}
