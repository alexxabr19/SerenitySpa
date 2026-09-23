package edu.itm.SerenitySpa.repositories;


import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientesDAOHelper {
    @Autowired
    private Conexion conexion;

    public static final String LISTAR_CLIENTES = "SELECT * FROM cliente";
    public static final String BUSCAR_CLIENTE = "SELECT * FROM cliente WHERE idCliente = ?";

    public static final String INSERTAR_CLIENTE = "INSERT INTO cliente (nombre, apellido, correo, telefono) VALUES (?, ?, ?, ?)";

    public static final String ACTUALIZAR_CLIENTE ="UPDATE cliente SET nombre = ?, apellido = ?, correo = ?, telefono = ? WHERE idCliente = ?";

    public static final String ELIMINAR_CLIENTE ="DELETE FROM cliente WHERE idCliente = ?";

    public List<Cliente> listarClientes()
    {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTAR_CLIENTES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //Actualizar
    public boolean actualizarCliente(Cliente cliente) {
        try (Connection conn = conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_CLIENTE)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //borrar
    public boolean eliminarCliente(int id) {
        try (Connection conn = conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ELIMINAR_CLIENTE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //nuevo
    public boolean insertarCliente(Cliente cliente) {
        try (Connection conn = conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTAR_CLIENTE)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
