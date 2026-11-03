package edu.itm.SerenitySpa;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.exceptions.ServicioEnUsoException;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.repositories.IServicioRepository;
import edu.itm.SerenitySpa.services.ServicioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioServiceTest {

    @Mock
    IServicioRepository servicioRepository;

    @InjectMocks
    ServicioService servicioService;

    @Test
    void listarDebeRetornarServicios() {
        when(servicioRepository.findAll()).thenReturn(List.of(new Servicio()));
        assertEquals(1, servicioService.listar().size());
    }

    @Test
    void buscarDebeRetornarServicio() {
        Servicio servicio = new Servicio();
        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));
        assertSame(servicio, servicioService.buscar(1));
    }

    @Test
    void eliminarDebeLanzarServicioEnUsoExceptionCuandoHayConflictoBD() {
        Servicio servicio = new Servicio();
        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));
        doThrow(DataIntegrityViolationException.class).when(servicioRepository).delete(servicio);

        assertThrows(ServicioEnUsoException.class, () -> servicioService.eliminar(1));
    }
}