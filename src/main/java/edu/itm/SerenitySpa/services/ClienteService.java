package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.repositories.IClienteDAO;
import edu.itm.SerenitySpa.repositories.IClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService implements IClienteService {

    private final IClienteRepository clienteRepository;
    private final IClienteDAO clienteDAO;

    public ClienteService(IClienteRepository clienteRepository, IClienteDAO clienteDAO) {
        this.clienteRepository = clienteRepository;
        this.clienteDAO = clienteDAO;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteDAO.listar();
    }

    @Override
    public Cliente buscarCliente(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado: " + id);
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        cliente.setIdCliente(0);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(int id, Cliente cliente) {
        Cliente actual = buscarCliente(id);
        actual.setNombre(cliente.getNombre());
        actual.setApellido(cliente.getApellido());
        actual.setCorreo(cliente.getCorreo());
        actual.setTelefono(cliente.getTelefono());
        return clienteRepository.save(actual);
    }

    @Override
    public void eliminarCliente(int id) {
        Cliente actual = buscarCliente(id);
        clienteRepository.delete(actual);
    }
}