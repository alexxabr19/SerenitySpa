package edu.itm.SerenitySpa.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Servicio
{
    private int idServicio;
    private String nombre;
    private String descripcion;
    private int duracion; 
    private double precio;
}
