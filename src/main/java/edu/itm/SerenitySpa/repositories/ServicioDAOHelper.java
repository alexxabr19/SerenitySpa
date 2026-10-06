package edu.itm.SerenitySpa.repositories;

import org.springframework.stereotype.Component;

@Component
public class ServicioDAOHelper {

    public static final String LISTAR_SERVICIOS = "SELECT * FROM servicio";

    public static final String BUSCAR_SERVICIO = "SELECT * FROM servicio WHERE idServicio = ?";

    public static final String INSERTAR_SERVICIO = "INSERT INTO servicio (nombre, descripcion, duracion, precio) VALUES (?, ?, ?, ?)";

    public static final String ACTUALIZAR_SERVICIO = "UPDATE servicio SET nombre = ?, descripcion = ?, duracion = ?, precio = ? WHERE idServicio = ?";

    public static final String ELIMINAR_SERVICIO = "DELETE FROM servicio WHERE idServicio = ?";
}
