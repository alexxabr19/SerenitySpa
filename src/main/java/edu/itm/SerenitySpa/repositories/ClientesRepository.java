package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Cliente;
import edu.itm.SerenitySpa.utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientesRepository {

    private Conexion conexion = new Conexion();

    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = ClientesDAOHelper.LISTAR_CLIENTES;

        try {

            Connection con = conexion.obtenerConexion();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setTelefono(rs.getString("telefono"));

                clientes.add(cliente);
            }

        } catch (Exception e) {

            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public Cliente buscarCliente(int idCliente) {

        Cliente cliente = null;

        String sql = ClientesDAOHelper.BUSCAR_CLIENTE;

        try {

            Connection con = conexion.obtenerConexion();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setTelefono(rs.getString("telefono"));
            }

        } catch (Exception e) {

            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

    public boolean insertarCliente(Cliente cliente) {

        String sql = ClientesDAOHelper.INSERTAR_CLIENTE;

        try {

            Connection con = conexion.obtenerConexion();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al insertar cliente: " + e.getMessage());

            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {

        String sql = ClientesDAOHelper.ACTUALIZAR_CLIENTE;

        try {

            Connection con = conexion.obtenerConexion();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al actualizar cliente: " + e.getMessage());

            return false;
        }
    }

    public boolean eliminarCliente(int idCliente) {

        String sql = ClientesDAOHelper.ELIMINAR_CLIENTE;

        try {

            Connection con = conexion.obtenerConexion();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al eliminar cliente: " + e.getMessage());

            return false;
        }
    }
}
