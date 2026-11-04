package edu.itm.SerenitySpa.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FacturaRequest {

    private LocalDate fecha;
    private double montoTotal;
    private String metodoPago;
    private String estado;
    private int idCita;
}