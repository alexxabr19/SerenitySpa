package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Sede;
import edu.itm.SerenitySpa.services.ISedeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sedes")
public class SedesController {
    private final ISedeService sedeService;
    public SedesController(ISedeService sedeService) {
        this.sedeService = sedeService;
    }
    @GetMapping
    public List<Sede> listar() {
        return sedeService.listar();
    }
    @GetMapping("/{id}")
    public Sede buscar(@PathVariable int id) {
        return sedeService.buscar(id);
    }
    @PostMapping
    public Sede guardar(@RequestBody Sede sede) {
        return sedeService.guardar(sede);
    }
    @PutMapping("/{id}")
    public Sede actualizar( @PathVariable int id, @RequestBody Sede sede) {
        return sedeService.actualizar(id, sede);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        sedeService.eliminar(id);
    }
}
