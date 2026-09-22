package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.services.ClientesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private ClientesService clientesService = new ClientesService();

    @GetMapping
    public List<Cliente> listarClientes() {

        return clientesService.listarClientes();
    }

    @GetMapping("/{idCliente}")
    public Cliente buscarCliente(@PathVariable int idCliente) {

        return clientesService.buscarCliente(idCliente);
    }

    @PostMapping
    public String insertarCliente(@RequestBody Cliente cliente) {

        if (clientesService.insertarCliente(cliente)) {
            return "Cliente registrado correctamente";
        }

        return "No se pudo registrar el cliente";
    }

    @PutMapping
    public String actualizarCliente(@RequestBody Cliente cliente) {

        if (clientesService.actualizarCliente(cliente)) {
            return "Cliente actualizado correctamente";
        }

        return "No se pudo actualizar el cliente";
    }

    @DeleteMapping("/{idCliente}")
    public String eliminarCliente(@PathVariable int idCliente) {

        if (clientesService.eliminarCliente(idCliente)) {
            return "Cliente eliminado correctamente";
        }

        return "No se pudo eliminar el cliente";
    }
}
