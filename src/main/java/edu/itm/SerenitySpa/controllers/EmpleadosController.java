package edu.itm.SerenitySpa.controllers;

import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.services.IEmpleadoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")

public class EmpleadosController {
    private final IEmpleadoService empleadoService;

    public EmpleadosController(IEmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @GetMapping("/{id}")
    public Empleado buscar(@PathVariable int id) {
        return empleadoService.buscar(id);
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        return empleadoService.actualizar(id, empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        empleadoService.eliminar(id);

    }


}
