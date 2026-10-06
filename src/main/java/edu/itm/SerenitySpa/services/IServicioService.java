package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Servicio;

import java.util.List;

public interface IServicioService {

    List<Servicio> listar();

    Servicio buscar(int id);                         // devuelve null si no existe

    Servicio guardar(Servicio servicio);

    Servicio actualizar(int id, Servicio servicio);  // devuelve null si no existe

    boolean eliminar(int id);                        // devuelve false si no existe
}
