package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cita;
import java.util.List;
import edu.itm.SerenitySpa.dto.CitaRequest;
public interface ICitaService {

    List<Cita> listar();

    Cita buscar(int id);

    Cita agendar(CitaRequest cita);

    Cita actualizar(int id, CitaRequest cita);

    void cancelar(int id);
}
