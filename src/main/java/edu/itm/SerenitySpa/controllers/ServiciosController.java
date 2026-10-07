package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.exceptions.ServicioEnUsoException;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.services.IServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios")
public class ServiciosController {

    @Autowired
    private IServicioService servicioService;

    @GetMapping
    @Operation(summary = "Lista los servicios", description = "Responde 200 con la lista de servicios")
    public ResponseEntity<List<Servicio>> listar() {
        try {
            return new ResponseEntity<>(servicioService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta un servicio por id", description = "Responde 200, o 404 si no existe")
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

    @PostMapping
    @Operation(summary = "Crea un servicio", description = "Responde 201, o 400 si los datos son incorrectos")
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

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un servicio", description = "Responde 200, 400 si los datos son incorrectos o 404 si no existe")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un servicio", description = "Responde 204, 404 si no existe o 409 si está asociado a citas o empleados")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {

        try {
            if (servicioService.eliminar(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (ServicioEnUsoException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Regla de negocio: nombre obligatorio, duración mayor a 0 y precio mayor a 0
    private boolean datosValidos(Servicio servicio) {
        return servicio.getNombre() != null
                && !servicio.getNombre().isBlank()
                && servicio.getDuracion() > 0
                && servicio.getPrecio() > 0;
    }
}