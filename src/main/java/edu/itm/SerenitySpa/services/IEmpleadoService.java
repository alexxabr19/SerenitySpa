package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Empleado;
import java.util.List;

public interface IEmpleadoService {
    List<Empleado> listar();
    Empleado buscar(int id);
    Empleado guardar(Empleado empleado);
    Empleado actualizar(int id, Empleado empleado);
    void eliminar(int id);

}
