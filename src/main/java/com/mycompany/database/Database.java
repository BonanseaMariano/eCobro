package com.mycompany.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    protected Connection conexion;

    public void conectar() {
        try {
            // Crea la base de datos
            String dbFile = "eCobro.db";
            conexion = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

            // Crea las tablas
            Statement stmt = conexion.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS vehiculo (id INTEGER PRIMARY KEY, patente TEXT, horaEntrada TEXT, uLat REAL, uLon REAL, calle TEXT)");
        } catch (SQLException e) {
            System.err.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conexion;
    }

    public void cerrar() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}