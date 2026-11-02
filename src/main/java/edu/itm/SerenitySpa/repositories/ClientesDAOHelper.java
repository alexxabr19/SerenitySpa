package edu.itm.SerenitySpa.repositories;

public final class ClientesDAOHelper {
    private ClientesDAOHelper() {}
    public static final String LISTAR =
            "SELECT idCliente, nombre, apellido, correo, telefono FROM cliente";
    public static final String BUSCAR_POR_ID =
            "SELECT idCliente, nombre, apellido, correo, telefono FROM cliente WHERE idCliente = ?";
    public static final String INSERTAR =
            "INSERT INTO cliente(nombre, apellido, correo, telefono) VALUES (?,?,?,?)";
    public static final String ACTUALIZAR =
            "UPDATE cliente SET nombre = ?, apellido = ?, correo = ?, telefono = ?";
    public static final String ELIMINAR =
            "DELETE FROM cliente WHERE idCliente = ?";
}
