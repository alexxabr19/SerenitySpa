package edu.itm.SerenitySpa.exceptions;

public class ServicioEnUsoException extends RuntimeException {

    public ServicioEnUsoException(String mensaje) {
        super(mensaje);
    }

    public ServicioEnUsoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
 