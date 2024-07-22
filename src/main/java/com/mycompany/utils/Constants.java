package com.mycompany.utils;

import org.jxmapviewer.viewer.GeoPosition;

import java.io.*;
import java.util.Properties;

public class Constants {
    private static final Properties properties = new Properties();

    //Bloque de inicializacion estatica de la clase (Distinto de un constructor)
    static {
        InputStream inputStream = null;

        //Cargo archivo de propiedades
        try {
            //Abro archivo de propiedades
            inputStream = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) { // Si el archivo no existe
            // Archivo de configuracion no encontrado
            System.err.println("Archivo de propiedades no encontrado, se creará uno con los valores por defecto");
            // Configurar valores por defecto
            properties.setProperty("precio.porMediaHora", "2500.0");
            properties.setProperty("zoom.default", "3");
            properties.setProperty("distancia.minima", "4.0");

            // Crear el archivo config.properties
            try (OutputStream output = new FileOutputStream("config.properties")) {
                properties.store(output, "Archivo de configuracion por defecto, reemplace los valores por los deseados");
                inputStream = new FileInputStream("config.properties");
            } catch (IOException e2) {
                System.err.println("Error al crear el archivo de propiedades: " + e2.getMessage());
                System.exit(1);
            }
        }

        //Cargo archivo de propiedades
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de propiedades: " + e.getMessage());
            System.exit(1);
        }
    }

    //Constantes
    public static final GeoPosition COORDENADAS_PMY = new GeoPosition(-42.766575, -65.033028);
    public static final Double PRECIO_POR_MEDIAHORA = Double.parseDouble(properties.getProperty("precio.porMediaHora", "2500.0"));
    public static final int DFAULT_ZOOM = Integer.parseInt(properties.getProperty("zoom.default", "3"));
    public static final Double MIN_DIST_CLIC = Double.parseDouble(properties.getProperty("distancia.minima", "4.0"));
}