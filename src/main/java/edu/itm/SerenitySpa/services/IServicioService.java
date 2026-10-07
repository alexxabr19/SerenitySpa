package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Servicio;
import java.util.List;

public interface IServicioService {
    List<Servicio> listar();
    Servicio buscar(int id);
    Servicio guardar(Servicio servicio);
    Servicio actualizar(int id, Servicio servicio);
    boolean eliminar(int id);
}