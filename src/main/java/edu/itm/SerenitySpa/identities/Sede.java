package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "sede")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSede;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;
}
