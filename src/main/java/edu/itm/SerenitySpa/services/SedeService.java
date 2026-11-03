package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
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
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró la sede con ID: " + id
                        ));
    }

    @Override
    public Sede guardar(Sede sede) {

        validarSede(sede);

        sede.setIdSede(0);

        return sedeRepository.save(sede);
    }

    @Override
    public Sede actualizar(int id, Sede sede) {

        Sede existente = buscar(id);

        validarSede(sede);

        existente.setNombre(sede.getNombre());
        existente.setDireccion(sede.getDireccion());
        existente.setTelefono(sede.getTelefono());
        existente.setCiudad(sede.getCiudad());

        return sedeRepository.save(existente);
    }

    @Override
    public void eliminar(int id) {

        Sede sede = buscar(id);

        sedeRepository.delete(sede);
    }

    private void validarSede(Sede sede) {

        if (sede == null) {
            throw new IllegalArgumentException(
                    "Los datos de la sede son obligatorios."
            );
        }

        if (sede.getNombre() == null
                || sede.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la sede es obligatorio."
            );
        }

        if (sede.getDireccion() == null
                || sede.getDireccion().isBlank()) {
            throw new IllegalArgumentException(
                    "La dirección de la sede es obligatoria."
            );
        }
    }
}