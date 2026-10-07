package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cliente;
import java.util.List;

public interface IClienteService {
    List<Cliente> listarClientes();
    Cliente buscarCliente(int id);
    Cliente guardarCliente(Cliente cliente);
    Cliente actualizarCliente(int id, Cliente cliente);
    void eliminarCliente(int id);
}