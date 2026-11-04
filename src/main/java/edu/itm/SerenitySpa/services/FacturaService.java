
package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.dto.FacturaRequest;
import edu.itm.SerenitySpa.exceptions.ReglaNegocioException;
import edu.itm.SerenitySpa.exceptions.RecursoNoEncontradoException;
import edu.itm.SerenitySpa.identities.Cita;
import edu.itm.SerenitySpa.identities.Factura;
import edu.itm.SerenitySpa.repositories.ICitaRepository;
import edu.itm.SerenitySpa.repositories.IFacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FacturaService implements IFacturaService {

    private final IFacturaRepository facturaRepository;
    private final ICitaRepository citaRepository;

    public FacturaService(
            IFacturaRepository facturaRepository,
            ICitaRepository citaRepository) {
        this.facturaRepository = facturaRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public List<Factura> listar() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura buscar(int id) {
        return facturaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Factura no encontrada"));
    }

    @Override
    public Factura crear(FacturaRequest request) {

        if (request == null) {
            throw new ReglaNegocioException("Los datos de la factura son obligatorios");
        }

        if (request.getMontoTotal() <= 0) {
            throw new ReglaNegocioException("El monto total debe ser mayor que cero");
        }

        if (request.getFecha() == null) {
            throw new ReglaNegocioException("La fecha de la factura es obligatoria");
        }

        if (request.getMetodoPago() == null || request.getMetodoPago().isBlank()) {
            throw new ReglaNegocioException("El método de pago es obligatorio");
        }

        if (request.getEstado() == null || request.getEstado().isBlank()) {
            throw new ReglaNegocioException("El estado de la factura es obligatorio");
        }

        if (request.getIdCita() <= 0) {
            throw new ReglaNegocioException("Debe seleccionar una cita válida");
        }

        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("La cita no existe"));

        if (facturaRepository.existsByCitaIdCita(request.getIdCita())) {
            throw new ReglaNegocioException(
                    "Esta cita ya tiene una factura registrada");
        }

        Factura factura = Factura.builder()
                .fecha(request.getFecha())
                .montoTotal(request.getMontoTotal())
                .metodoPago(request.getMetodoPago())
                .estado(request.getEstado())
                .cita(cita)
                .build();

        return facturaRepository.save(factura);
    }

    @Override
    public Factura actualizar(int id, FacturaRequest request) {

        if (request == null) {
            throw new ReglaNegocioException("Los datos de la factura son obligatorios");
        }

        Factura factura = buscar(id);

        if (request.getMontoTotal() <= 0) {
            throw new ReglaNegocioException("El monto total debe ser mayor que cero");
        }

        if (request.getFecha() == null) {
            throw new ReglaNegocioException("La fecha de la factura es obligatoria");
        }

        if (request.getMetodoPago() == null || request.getMetodoPago().isBlank()) {
            throw new ReglaNegocioException("El método de pago es obligatorio");
        }

        if (request.getEstado() == null || request.getEstado().isBlank()) {
            throw new ReglaNegocioException("El estado de la factura es obligatorio");
        }

        if (request.getIdCita() <= 0) {
            throw new ReglaNegocioException("Debe seleccionar una cita válida");
        }

        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("La cita no existe"));

        if (facturaRepository.existsByCitaIdCita(request.getIdCita())
                && factura.getCita().getIdCita() != request.getIdCita()) {
            throw new ReglaNegocioException(
                    "Esta cita ya tiene otra factura registrada");
        }

        factura.setFecha(request.getFecha());
        factura.setMontoTotal(request.getMontoTotal());
        factura.setMetodoPago(request.getMetodoPago());
        factura.setEstado(request.getEstado());
        factura.setCita(cita);

        return facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        Factura factura = buscar(id);

        Cita cita = factura.getCita();

        if (cita != null) {
            cita.setFactura(null);
        }

        facturaRepository.delete(factura);
        facturaRepository.flush();
    }
}
