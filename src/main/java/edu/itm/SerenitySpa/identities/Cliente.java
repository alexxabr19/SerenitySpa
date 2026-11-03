package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private int idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Cita> citas;



}

