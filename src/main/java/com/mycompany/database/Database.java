package com.mycompany.database;

import com.mycompany.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    protected Connection conexion;


    /**
     * Conecta con la base de datos y si no existe la crea
     */
    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            if (ex instanceof SQLException && ((SQLException) ex).getSQLState().equals("42000")) {
                // La base de datos no existe
                try {
                    crearBaseDeDatos();
                    // Intentar conectar de nuevo solo si la base de datos se creó exitosamente
                    conectar();
                } catch (SQLException ex2) {
                    // Manejar la excepción de crearBaseDeDatos() aquí
                    System.err.println("Error al crear la base de datos: " + ex2.getMessage());
                }
            } else {
                // Manejar otras excepciones aquí
                System.err.println("Error de conexión con la Base de datos: " + ex.getMessage());
                // Terminar el programa
                System.exit(1);
            }
        }
    }

    /**
     * Crea la base de datos y la tabla correspondiente
     *
     * @throws SQLException Si la base de datos no pudo ser creada correctamente
     */
    private void crearBaseDeDatos() throws SQLException {
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USER, Constants.PASS)) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS ecobro");
            statement.executeUpdate("USE ecobro");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `vehiculo` ( " +
                    "`patente` varchar(7) NOT NULL, " +
                    "`horaEntrada` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(), " +
                    "`calle` varchar(50) DEFAULT NULL, " +
                    "`uLat` double DEFAULT NULL, " +
                    "`uLon` double DEFAULT NULL, " +
                    "PRIMARY KEY (`patente`) " +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
        }
    }

    /**
     * Cierra la conexión con la base de datos
     *
     * @throws SQLException error al cerrar la base de datos
     */
    public void cerrar() throws SQLException {
        if (conexion != null) {
            if (!conexion.isClosed()) {
                conexion.close();
            }
        }
    }
}
