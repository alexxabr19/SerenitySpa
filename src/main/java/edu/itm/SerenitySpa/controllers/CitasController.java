package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.dto.CitaRequest;
import edu.itm.SerenitySpa.identities.Cita;
import edu.itm.SerenitySpa.services.ICitaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Citas", description = "Operaciones para gestionar las citas del spa")
@RestController
@RequestMapping("/api/citas")
public class CitasController {

    private final ICitaService citaService;

    public CitasController(ICitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Listar citas", description = "Retorna todas las citas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Citas consultadas correctamente"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    @Operation(summary = "Buscar cita por ID", description = "Retorna la información detallada de una cita por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/{id}")
    public Cita buscar(@PathVariable int id) {
        return citaService.buscar(id);
    }

    @Operation(summary = "Agendar cita", description = "Registra una nueva cita validando disponibilidad y reglas de negocio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita agendada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos requeridos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: Cruce de horarios o empleado no habilitado"),
            @ApiResponse(responseCode = "404", description = "Cliente, servicio o empleado inexistente")
    })
    @PostMapping
    public Cita agendar(@RequestBody CitaRequest cita) {
        return citaService.agendar(cita);
    }

    @Operation(summary = "Actualizar cita", description = "Actualiza los datos de una cita existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable int id, @RequestBody CitaRequest cita) {
        return citaService.actualizar(id, cita);
    }

    @Operation(summary = "Cancelar cita", description = "Cambia el estado de una cita a cancelada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable int id) {
        citaService.cancelar(id);
    }
}
