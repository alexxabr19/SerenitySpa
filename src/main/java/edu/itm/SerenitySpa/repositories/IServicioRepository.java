package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Servicio;

import java.util.List;

public interface IServicioRepository {

    List<Servicio> listarServicios();

    Servicio buscarServicio(int idServicio);        // devuelve null si no existe

    Servicio insertarServicio(Servicio servicio);   // devuelve el servicio con su id generado

    boolean actualizarServicio(Servicio servicio);  // false si no existe

    boolean eliminarServicio(int idServicio);       // false si no existe
}
