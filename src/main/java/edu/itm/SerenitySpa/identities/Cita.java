package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCita;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idSede")
    private Sede sede;
}
