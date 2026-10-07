package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cita;

import java.util.List;

public interface ICitaService {

    List<Cita> listar();

    Cita buscar(int id);

    Cita agendar(Cita cita);

    Cita actualizar(int id, Cita cita);

    void cancelar(int id);
}
