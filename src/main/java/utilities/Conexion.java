package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection obtenerConexion() {

        String url = "jdbc:mysql://localhost:3306/serenity_spa";
        String usuario = "root";
        String contrasena = "xime2512";

        try {
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        Conexion conexion = new Conexion();

        Connection con = conexion.obtenerConexion();

        if (con != null) {
            System.out.println("Conexion exitosa a la base de datos");
        } else {
            System.out.println("No se pudo conectar a la base de datos");
        }
    }
}