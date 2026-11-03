package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.repositories.IClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;

@Service
public class ClienteService implements IClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarCliente(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el cliente con ID: " + id
                        ));
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(int id, Cliente cliente) {

        Cliente existente = buscarCliente(id);

        validarCliente(cliente);

        existente.setNombre(cliente.getNombre().trim());
        existente.setApellido(cliente.getApellido().trim());
        existente.setCorreo(cliente.getCorreo().trim());
        existente.setTelefono(cliente.getTelefono().trim());

        return clienteRepository.save(existente);
    }

    @Override
    public void eliminarCliente(int id) {
        Cliente cliente = buscarCliente(id);
        clienteRepository.delete(cliente);
    }

    private void validarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Los datos del cliente son obligatorios."
            );
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio."
            );
        }

        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new IllegalArgumentException(
                    "El apellido del cliente es obligatorio."
            );
        }

        if (cliente.getCorreo() == null || cliente.getCorreo().isBlank()
                || !cliente.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException(
                    "El correo electrónico no es válido."
            );
        }

        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono del cliente es obligatorio."
            );
        }
    }
}