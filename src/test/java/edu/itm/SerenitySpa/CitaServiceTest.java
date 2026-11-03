package edu.itm.SerenitySpa;

import edu.itm.SerenitySpa.dto.CitaRequest;
import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.exceptions.ReglaNegocioException;
import edu.itm.SerenitySpa.identities.*;
import edu.itm.SerenitySpa.repositories.*;
import edu.itm.SerenitySpa.services.CitaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock ICitaRepository citaRepository;
    @Mock IClienteRepository clienteRepository;
    @Mock IServicioRepository servicioRepository;
    @Mock IEmpleadoRepository empleadoRepository;
    @Mock ISedeRepository sedeRepository;

    @InjectMocks
    CitaService citaService;

    @Test
    void listarDebeRetornarTodasLasCitas() {
        when(citaRepository.findAll()).thenReturn(List.of(new Cita()));
        assertEquals(1, citaService.listar().size());
        verify(citaRepository).findAll();
    }

    @Test
    void buscarDebeRetornarCitaExistente() {
        Cita cita = new Cita();
        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
        assertSame(cita, citaService.buscar(1));
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoNoExiste() {
        when(citaRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> citaService.buscar(99));
    }

    @Test
    void cancelarDebeCambiarEstadoACancelada() {
        Cita cita = new Cita();
        cita.setEstado("AGENDADA");
        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));

        citaService.cancelar(1);

        assertEquals("CANCELADA", cita.getEstado());
        verify(citaRepository).save(cita);
    }
}