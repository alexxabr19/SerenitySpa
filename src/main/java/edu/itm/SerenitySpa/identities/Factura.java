package edu.itm.SerenitySpa.identities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private int idFactura;
    private LocalDate fecha;
    private double montoTotal;
    private String metodoPago;
    private String estado;
    @OneToOne
    @JoinColumn(name = "idCita", nullable = false, unique = true)
    @JsonIgnore
    private Cita cita;
}
