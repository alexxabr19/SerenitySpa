package edu.itm.SerenitySpa.exceptions;
 
// Se lanza cuando se intenta eliminar un servicio que ya está en una cita o asignado a un empleado
public class ServicioEnUsoException extends RuntimeException {
 
    public ServicioEnUsoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
 