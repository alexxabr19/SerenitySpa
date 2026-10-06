package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Sede;
import edu.itm.SerenitySpa.repositories.ISedeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class SedeService implements ISedeService {

    private final ISedeRepository sedeRepository;
    public SedeService(ISedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }
    @Override
    public List<Sede> listar() {
        return sedeRepository.findAll();
    }
    @Override
    public Sede buscar(int id) {
        return sedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
    }
    @Override
    public Sede guardar(Sede sede) {
        return sedeRepository.save(sede);
    }
    @Override
    public Sede actualizar(int id, Sede sede) {
        Sede existente = buscar(id);
        existente.setNombre(sede.getNombre());
        existente.setDireccion(sede.getDireccion());
        existente.setTelefono(sede.getTelefono());
        existente.setCiudad(sede.getCiudad());
        return sedeRepository.save(existente);
    }
    @Override
    public void eliminar(int id) {
        Sede sede = buscar(id);
        sedeRepository.delete(sede); }

}
