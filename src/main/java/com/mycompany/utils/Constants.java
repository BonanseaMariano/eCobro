package com.mycompany.utils;

import org.jxmapviewer.viewer.GeoPosition;

import java.io.*;
import java.util.Properties;

public class Constants {
    private static final Properties properties = new Properties();

    //Base de datos
    public static String URL;
    public static String DB_URL;
    public static String USER;
    public static String PASS;

    //Constantes
    public static final GeoPosition COORDENADAS_PMY = new GeoPosition(-42.766575, -65.033028);
    public static Double PRECIO_POR_MEDIAHORA;
    public static int DFAULT_ZOOM;
    public static Double MIN_DIST_CLIC;

    //Bloque de inicializacion estatica de la clase para cargar las constantes desde el archivo (Distinto de un constructor)
    static {
        InputStream inputStream = null;

        //Cargo archivo de propiedades
        try {
            //Abro archivo de propiedades
            inputStream = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) { // Si el archivo no existe
            // Archivo de configuracion no encontrado
            System.err.println("Archivo de propiedades no encontrado, se creará uno con los valores por defecto");

            // Crear el archivo config.properties
            try (OutputStream output = new FileOutputStream("config.properties")) {
                properties.store(output, "Archivo de configuracion por defecto, reemplace los valores por los deseados");

                // Configurar valores por defecto
                output.write((
                        """
                                #\t---- BASE DE DATOS ----
                                db.root=jdbc:mysql://localhost
                                db.url=jdbc:mysql://localhost/ecobro
                                db.user=root
                                db.passwrd=
                                #Precio por cada media hora estacionado (DOUBLE)
                                precio.porMediaHora=2500.0
                                #Zoom por defecto de los mapas (INT)
                                zoom.default=3
                                #Distancia minima para seleccionar un vehiculo (DOUBLE)
                                distancia.minima=4.0"""
                ).getBytes());

                inputStream = new FileInputStream("config.properties");

            } catch (IOException e2) {
                System.err.println("Error al crear el archivo de propiedades: " + e2.getMessage());
                System.exit(1);
            }
        }

        //Cargo archivo de propiedades
        try {
            properties.load(inputStream);
            PRECIO_POR_MEDIAHORA = Double.parseDouble(properties.getProperty("precio.porMediaHora", "2500.0"));
            DFAULT_ZOOM = Integer.parseInt(properties.getProperty("zoom.default", "3"));
            MIN_DIST_CLIC = Double.parseDouble(properties.getProperty("distancia.minima", "4.0"));
            URL = properties.getProperty("db.root", "jdbc:mysql://localhost");
            DB_URL = properties.getProperty("db.url", "jdbc:mysql://localhost/ecobro");
            USER = properties.getProperty("db.user", "root");
            PASS = properties.getProperty("db.passwrd", "");
        } catch (Exception e) {
            System.err.println("Error al leer el archivo de propiedades, verifique que los valores en el archivo sean correctos \"config.properties\": " + e.getMessage());
            System.exit(1);
        }
    }
}