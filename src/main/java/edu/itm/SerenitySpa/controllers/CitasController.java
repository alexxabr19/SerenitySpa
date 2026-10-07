package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Cita;
import edu.itm.SerenitySpa.services.ICitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitasController {

    private final ICitaService citaService;

    public CitasController(ICitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    @GetMapping("/{id}")
    public Cita buscar(@PathVariable int id) {
        return citaService.buscar(id);
    }

    @PostMapping
    public Cita agendar(@RequestBody Cita cita) {
        return citaService.agendar(cita);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable int id,@RequestBody Cita cita) {

        return citaService.actualizar(id, cita);
    }

    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable int id) {
        citaService.cancelar(id);
    }
}
