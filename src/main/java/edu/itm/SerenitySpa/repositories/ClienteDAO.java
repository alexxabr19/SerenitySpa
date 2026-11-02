package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.exceptions.AccesoDatosException;
import edu.itm.SerenitySpa.identities.Cliente;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteDAO implements IClienteDAO {
    private final DataSource dataSource;
    public ClienteDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Cliente mapear(ResultSet rs) throws  SQLException{
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getString("telefono"));
        return c;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement (ClientesDAOHelper.LISTAR);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()) clientes.add(mapear(rs));
            return clientes;
        } catch(SQLException e) {
            throw new AccesoDatosException("No fue posible listar a los clientes.", e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorID(int id){
        try(Connection cn = dataSource.getConnection();
        PreparedStatement ps = cn.prepareStatement(ClientesDAOHelper.BUSCAR_POR_ID)){
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }catch (SQLException e) {
            throw new AccesoDatosException("No fue posible consultar el cliente.", e);
        }
    }

    @Override
    public Cliente insertar(Cliente cliente) {
        try(Connection cn = dataSource.getConnection();
        PreparedStatement ps = cn.prepareStatement(ClientesDAOHelper.INSERTAR,
                Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()) cliente.setIdCliente(keys.getInt(1));
            }
            return cliente;
        }catch (SQLException e) {
            throw new AccesoDatosException("No fue posible registrar al cliente. ", e);
        }
    }

    @Override
    public boolean actualizar(Cliente cliente){
        try(Connection cn = dataSource.getConnection();
        PreparedStatement ps = cn.prepareStatement(ClientesDAOHelper.ACTUALIZAR)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException("No fue posible actualizar el cliente. ", e) ;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection cn = dataSource.getConnection();
        PreparedStatement ps = cn.prepareStatement(ClientesDAOHelper.ELIMINAR)){
            ps.setInt(1,id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException("No fue posible eliminar el cliente.", e);
        }
    }

}
