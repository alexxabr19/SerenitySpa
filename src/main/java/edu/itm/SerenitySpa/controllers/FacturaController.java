package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.dto.FacturaRequest;
import edu.itm.SerenitySpa.identities.Factura;
import edu.itm.SerenitySpa.services.IFacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Facturas", description = "Operaciones para gestionar las facturas del spa")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final IFacturaService facturaService;

    public FacturaController(IFacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Operation(summary = "Listar facturas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Facturas consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Factura> listar() {
        return facturaService.listar();
    }

    @Operation(summary = "Buscar factura por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public Factura buscar(@PathVariable int id) {
        return facturaService.buscar(id);
    }

    @Operation(summary = "Crear factura")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura creada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
            @ApiResponse(responseCode = "409", description = "Regla de negocio incumplida"),
            @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    })
    @PostMapping
    public Factura crear(@RequestBody FacturaRequest factura) {
        return facturaService.crear(factura);
    }

    @Operation(summary = "Actualizar factura")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Factura o cita no encontrada"),
            @ApiResponse(responseCode = "409", description = "Regla de negocio incumplida"),
            @ApiResponse(responseCode = "400", description = "Datos de petición inválidos")
    })
    @PutMapping("/{id}")
    public Factura actualizar(
            @PathVariable int id,
            @RequestBody FacturaRequest factura) {
        return facturaService.actualizar(id, factura);
    }

    @Operation(summary = "Eliminar factura")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        facturaService.eliminar(id);
    }
}