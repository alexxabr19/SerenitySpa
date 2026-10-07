package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.exceptions.ServicioEnUsoException;
import edu.itm.SerenitySpa.identities.Servicio;
import edu.itm.SerenitySpa.utilities.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioRepository implements IServicioRepository {

    private final Conexion conexion;

    public ServicioRepository(Conexion conexion) {
        this.conexion = conexion;
    }

    private static final String LISTAR_SERVICIOS =
            "SELECT * FROM servicio";

    private static final String BUSCAR_SERVICIO =
            "SELECT * FROM servicio WHERE idServicio = ?";

    private static final String INSERTAR_SERVICIO =
            "INSERT INTO servicio (nombre, descripcion, duracion, precio) VALUES (?, ?, ?, ?)";

    private static final String ACTUALIZAR_SERVICIO =
            "UPDATE servicio SET nombre = ?, descripcion = ?, duracion = ?, precio = ? WHERE idServicio = ?";

    private static final String ELIMINAR_SERVICIO =
            "DELETE FROM servicio WHERE idServicio = ?";


    @Override
    public List<Servicio> listarServicios() {

        List<Servicio> servicios = new ArrayList<>();

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(LISTAR_SERVICIOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                servicios.add(convertirServicio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar servicios", e);
        }

        return servicios;
    }


    @Override
    public Servicio buscarServicio(int idServicio) {

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(BUSCAR_SERVICIO)) {

            ps.setInt(1, idServicio);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirServicio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el servicio", e);
        }

        return null;
    }


    @Override
    public Servicio insertarServicio(Servicio servicio) {

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     INSERTAR_SERVICIO,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setInt(3, servicio.getDuracion());
            ps.setDouble(4, servicio.getPrecio());

            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {

                if (claves.next()) {
                    servicio.setIdServicio(claves.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el servicio", e);
        }

        return servicio;
    }


    @Override
    public boolean actualizarServicio(Servicio servicio) {

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(ACTUALIZAR_SERVICIO)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setInt(3, servicio.getDuracion());
            ps.setDouble(4, servicio.getPrecio());
            ps.setInt(5, servicio.getIdServicio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el servicio", e);
        }
    }


    @Override
    public boolean eliminarServicio(int idServicio) {

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(ELIMINAR_SERVICIO)) {

            ps.setInt(1, idServicio);

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {

            throw new ServicioEnUsoException(
                    "El servicio está asociado a otros registros",
                    e
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el servicio", e);
        }
    }


    private Servicio convertirServicio(ResultSet rs) throws SQLException {

        Servicio servicio = new Servicio();

        servicio.setIdServicio(rs.getInt("idServicio"));
        servicio.setNombre(rs.getString("nombre"));
        servicio.setDescripcion(rs.getString("descripcion"));
        servicio.setDuracion(rs.getInt("duracion"));
        servicio.setPrecio(rs.getDouble("precio"));

        return servicio;
    }
}