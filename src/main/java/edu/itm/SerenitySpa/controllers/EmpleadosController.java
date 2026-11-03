package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.services.IEmpleadoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Empleados", description = "Operaciones para gestionar el personal del spa")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadosController {
    private final IEmpleadoService empleadoService;

    public EmpleadosController(IEmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Operation(summary = "Listar empleados", description = "Retorna todos los empleados registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleados consultados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @Operation(summary = "Buscar empleado por ID", description = "Retorna los datos de un empleado específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public Empleado buscar(@PathVariable int id) {
        return empleadoService.buscar(id);
    }

    @Operation(summary = "Guardar empleado", description = "Registra un nuevo empleado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @Operation(summary = "Actualizar empleado", description = "Actualiza la información de un empleado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        return empleadoService.actualizar(id, empleado);
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        empleadoService.eliminar(id);

    }


}
