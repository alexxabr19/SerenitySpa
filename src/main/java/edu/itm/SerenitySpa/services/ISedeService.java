package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Sede;
import java.util.List;

public interface ISedeService {
    List<Sede> listar();
    Sede buscar(int id);
    Sede guardar(Sede sede);
    Sede actualizar(int id, Sede sede);
    void eliminar(int id);
}
