package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
@Entity @Table(name = "sede")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSede")
    private int idSede;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;

    @OneToMany(mappedBy = "sede")
    @JsonIgnore
    private List<Cita> citas;
}
