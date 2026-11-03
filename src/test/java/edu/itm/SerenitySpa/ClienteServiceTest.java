package edu.itm.SerenitySpa;

import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.repositories.IClienteRepository;
import edu.itm.SerenitySpa.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    IClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    @Test
    void listarClientesDebeRetornarLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente()));
        assertEquals(1, clienteService.listarClientes().size());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscarClienteDebeRetornarClienteExistente() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        assertSame(cliente, clienteService.buscarCliente(1));
    }

    @Test
    void guardarClienteDebeRegistrarExitosamente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana");
        cliente.setApellido("Pérez");
        cliente.setCorreo("ana@example.com");
        cliente.setTelefono("3001234567");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Versión limpia:
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente guardado = clienteService.guardarCliente(cliente);

        assertNotNull(guardado);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void guardarClienteInvalidoDebeLanzarExcepcion() {
        Cliente cliente = new Cliente(); // Sin datos obligatorios
        assertThrows(IllegalArgumentException.class, () -> clienteService.guardarCliente(cliente));
    }
}