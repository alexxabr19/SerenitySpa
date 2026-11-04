package edu.itm.SerenitySpa;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.repositories.IEmpleadoRepository;
import edu.itm.SerenitySpa.repositories.IServicioRepository;
import edu.itm.SerenitySpa.services.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    IEmpleadoRepository empleadoRepository;

    @Mock
    IServicioRepository servicioRepository;

    @InjectMocks
    EmpleadoService empleadoService;

    @Test
    void listarDebeRetornarEmpleados() {
        when(empleadoRepository.findAll()).thenReturn(List.of(new Empleado()));
        assertEquals(1, empleadoService.listar().size());
        verify(empleadoRepository).findAll();
    }

    @Test
    void buscarDebeRetornarEmpleadoExistente() {
        Empleado empleado = new Empleado();
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        assertSame(empleado, empleadoService.buscar(1));
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoNoExiste() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> empleadoService.buscar(99));
    }

    @Test
    void guardarEmpleadoDebeRegistrarExitosamente() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Carlos Pérez");
        empleado.setCorreo("carlos@example.com");

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        Empleado guardado = empleadoService.guardar(empleado);

        assertNotNull(guardado);
        verify(empleadoRepository).save(empleado);
    }

    @Test
    void guardarEmpleadoInvalidoDebeLanzarExcepcion() {
        Empleado empleado = new Empleado();
        assertThrows(IllegalArgumentException.class, () -> empleadoService.guardar(empleado));
    }
}
