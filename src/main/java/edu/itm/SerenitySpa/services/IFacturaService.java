package edu.itm.SerenitySpa.services;

import edu.itm.SerenitySpa.dto.FacturaRequest;
import edu.itm.SerenitySpa.identities.Factura;

import java.util.List;

public interface IFacturaService {

    List<Factura> listar();

    Factura buscar(int id);

    Factura crear(FacturaRequest factura);

    Factura actualizar(int id, FacturaRequest factura);

    void eliminar(int id);
}