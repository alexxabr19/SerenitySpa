package edu.itm.SerenitySpa;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Sede;
import edu.itm.SerenitySpa.repositories.ISedeRepository;
import edu.itm.SerenitySpa.services.SedeService;
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
class SedeServiceTest {

    @Mock
    ISedeRepository sedeRepository;

    @InjectMocks
    SedeService sedeService;

    @Test
    void listarDebeRetornarSedes() {
        when(sedeRepository.findAll()).thenReturn(List.of(new Sede()));
        assertEquals(1, sedeService.listar().size());
        verify(sedeRepository).findAll();
    }

    @Test
    void buscarDebeRetornarSedeExistente() {
        Sede sede = new Sede();
        when(sedeRepository.findById(1)).thenReturn(Optional.of(sede));
        assertSame(sede, sedeService.buscar(1));
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoNoExiste() {
        when(sedeRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> sedeService.buscar(99));
    }

    @Test
    void guardarSedeDebeRegistrarExitosamente() {
        Sede sede = new Sede();
        sede.setNombre("Sede El Poblado");
        sede.setDireccion("Calle 10 # 43-20");

        when(sedeRepository.save(any(Sede.class))).thenReturn(sede);

        Sede guardada = sedeService.guardar(sede);

        assertNotNull(guardada);
        verify(sedeRepository).save(sede);
    }

    @Test
    void guardarSedeInvalidaDebeLanzarExcepcion() {
        Sede sede = new Sede();
        assertThrows(IllegalArgumentException.class, () -> sedeService.guardar(sede));
    }
}