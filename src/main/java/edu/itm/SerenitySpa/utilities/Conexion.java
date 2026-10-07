package edu.itm.SerenitySpa.utilities;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Conexion {

    public Connection obtenerConexion() {

        String url = "jdbc:mysql://localhost:3306/serenity_spa";
        String usuario = "root";
        String contrasena = "12345";

        try {
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }
}