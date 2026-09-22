package edu.itm.SerenitySpa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @GetMapping
    public String listarClientes() {
        return "Lista de clientes";
    }

    @PostMapping
    public String registrarCliente(@RequestBody String cliente) {
        return "Cliente registrado";
    }
}
