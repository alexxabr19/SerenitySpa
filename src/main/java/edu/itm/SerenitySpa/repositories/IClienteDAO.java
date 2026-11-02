package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Cliente;
import java.util.List;
import java.util.Optional;

public interface IClienteDAO {
    List<Cliente> listar();
    Optional<Cliente> buscarPorID(int id);
    Cliente insertar(Cliente cliente);
    boolean actualizar(Cliente cliente);
    boolean eliminar(int id);
}
