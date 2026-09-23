package edu.itm.SerenitySpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.services.ClientesServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesServices clientesServices;

    @GetMapping
    public List<Cliente> listar()
    {
        return clientesServices.listarClientes();
    }
    @PutMapping
    public String actualizar(@RequestBody Cliente cliente) {
        boolean exito = clientesServices.actualizarCliente(cliente);
        return exito ? "Cliente actualizado exitosamente" : "Error al actualizar";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        boolean exito = clientesServices.eliminarCliente(id);
        return exito ? "Cliente eliminado exitosamente" : "Error al eliminar";
    }
    @PostMapping
    public String guardar(@RequestBody Cliente cliente) {
        boolean exito = clientesServices.insertarCliente(cliente);
        return exito ? "Cliente guardado exitosamente" : "Error al guardar cliente";
    }
}
