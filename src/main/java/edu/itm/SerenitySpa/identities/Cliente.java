package edu.itm.SerenitySpa.identities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente
{
    private int    idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
}

