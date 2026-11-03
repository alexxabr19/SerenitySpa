package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.services.IClienteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Clientes", description = "Operaciones para la gestión de clientes en el spa")
@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final IClienteService clienteService;

    public ClientesController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar clientes", description = "Retorna una lista con todos los clientes registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes consultados exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna la información de un cliente específico según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable int id) {
        return clienteService.buscarCliente(id);
    }

    @Operation(summary = "Guardar cliente", description = "Registra un nuevo cliente en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de petición inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    @Operation(summary = "Actualizar cliente", description = "Modifica la información de un cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable int id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        clienteService.eliminarCliente(id);
    }
}