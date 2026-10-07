package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.services.IClienteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final IClienteService clienteService;

    public ClientesController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable int id) {
        return clienteService.buscarCliente(id);
    }

    @PostMapping
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable int id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        clienteService.eliminarCliente(id);
    }
}