package com.mycompany.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    protected Connection conexion;
    private final String DB_URL = "jdbc:mysql://localhost/ecobro";
    private final String USER = "root";
    private final String PASS = "";

    /**
     * Conecta con la base de datos
     *
     * @throws ClassNotFoundException
     */
    public void conectar() throws ClassNotFoundException {
        try {
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la conexión con la base de datos
     *
     * @throws SQLException
     */
    public void cerrar() throws SQLException {
        if (conexion != null) {
            if (!conexion.isClosed()) {
                conexion.close();
            }
        }
    }
}
