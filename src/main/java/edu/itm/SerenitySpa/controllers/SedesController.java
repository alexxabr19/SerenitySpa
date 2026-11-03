package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Sede;
import edu.itm.SerenitySpa.services.ISedeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Sedes", description = "Operaciones para gestionar las sedes del spa")
@RestController
@RequestMapping("/api/sedes")
public class SedesController {
    private final ISedeService sedeService;
    public SedesController(ISedeService sedeService) {
        this.sedeService = sedeService;
    }

    @Operation(summary = "Listar sedes", description = "Retorna todas las sedes registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sedes consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Sede> listar() {
        return sedeService.listar();
    }

    @Operation(summary = "Buscar sede por ID", description = "Retorna la información de una sede específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede encontrada"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @GetMapping("/{id}")
    public Sede buscar(@PathVariable int id) {
        return sedeService.buscar(id);
    }

    @Operation(summary = "Guardar sede", description = "Registra una nueva sede.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sede creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Sede guardar(@RequestBody Sede sede) {
        return sedeService.guardar(sede);
    }

    @Operation(summary = "Actualizar sede", description = "Actualiza los datos de una sede existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @PutMapping("/{id}")
    public Sede actualizar( @PathVariable int id, @RequestBody Sede sede) {
        return sedeService.actualizar(id, sede);
    }

    @Operation(summary = "Eliminar sede", description = "Elimina una sede por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        sedeService.eliminar(id);
    }
}
