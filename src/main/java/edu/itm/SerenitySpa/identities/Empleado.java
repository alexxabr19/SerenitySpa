package edu.itm.SerenitySpa.identities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado")
    private int idEmpleado;
    private String nombre;
    private String cargo;
    private String correo;
    private String telefono;
    @ManyToMany
    @JoinTable(
            name = "empleado_servicio",
            joinColumns = @JoinColumn(name = "idEmpleado"),
            inverseJoinColumns = @JoinColumn(name = "idServicio")
    )
    private List<Servicio> serviciosHabilitados;

}



