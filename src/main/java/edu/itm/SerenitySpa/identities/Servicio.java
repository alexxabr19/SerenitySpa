package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private int idServicio;
    private String nombre;
    private String descripcion;
    private int duracion;
    private double precio;

    @OneToMany(mappedBy = "servicio")
    @JsonIgnore
    private List<Cita> citas;
}