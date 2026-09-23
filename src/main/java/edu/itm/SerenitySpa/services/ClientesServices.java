package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.repositories.ClientesDAOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesServices {

    @Autowired
    private ClientesDAOHelper clientesDAOHelper;

    public List<Cliente> listarClientes() {
        return clientesDAOHelper.listarClientes();
    }
    public boolean actualizarCliente(Cliente cliente) {
        return clientesDAOHelper.actualizarCliente(cliente);
    }
    public boolean eliminarCliente(int id) {
        return clientesDAOHelper.eliminarCliente(id);
    }
    public boolean insertarCliente(Cliente cliente) {
        return clientesDAOHelper.insertarCliente(cliente);
    }
}