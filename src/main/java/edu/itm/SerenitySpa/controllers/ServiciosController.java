package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.services.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Servicios", description = "Operaciones para administrar la oferta de servicios del spa")
@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios")
public class ServiciosController {

    @Autowired
    private IServicioService servicioService;

    @Operation(summary = "Listar servicios", description = "Retorna todos los servicios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicios consultados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        try {
            return new ResponseEntity<>(servicioService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Buscar servicio por ID", description = "Retorna la información de un servicio específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscar(@PathVariable("id") int id) {
        try {
            Servicio servicio = servicioService.buscar(id);

            if (servicio == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(servicio, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Guardar servicio", description = "Registra un nuevo servicio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Servicio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Servicio> guardar(@RequestBody Servicio servicio) {

        if (!datosValidos(servicio)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(
                    servicioService.guardar(servicio),
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar servicio", description = "Actualiza los datos de un servicio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(
            @PathVariable("id") int id,
            @RequestBody Servicio servicio) {

        if (!datosValidos(servicio)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Servicio actualizado = servicioService.actualizar(id, servicio);

            if (actualizado == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(actualizado, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio validando que no esté en uso.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio eliminado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El servicio está en uso"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {

        servicioService.eliminar(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean datosValidos(Servicio servicio) {
        return servicio.getNombre() != null
                && !servicio.getNombre().isBlank()
                && servicio.getDuracion() > 0
                && servicio.getPrecio() > 0;
    }
}