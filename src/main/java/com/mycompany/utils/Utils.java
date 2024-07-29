package com.mycompany.utils;

import org.jxmapviewer.viewer.GeoPosition;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class Utils {

    /**
     * Valida una patente ingresada con el formato "LLNNNLL" o "LLLNNN" (Patentes Argentinas 1994 y 2015)
     *
     * @param patente patente a validar
     * @return true si es correcta
     */
    public static boolean validarPatente(String patente) {
        if (patente.isEmpty()) {
            return false;
        }

        char[] caracteres = patente.toCharArray();

        return switch (caracteres.length) {
            case 6 ->
                    Character.isLetter(caracteres[0]) && Character.isLetter(caracteres[1]) && Character.isLetter(caracteres[2]) && Character.isDigit(caracteres[3]) &&
                            Character.isDigit(caracteres[4]) && Character.isDigit(caracteres[5]);
            case 7 ->
                    Character.isLetter(caracteres[0]) && Character.isLetter(caracteres[1]) && Character.isDigit(caracteres[2]) && Character.isDigit(caracteres[3]) &&
                            Character.isDigit(caracteres[4]) && Character.isLetter(caracteres[5]) && Character.isLetter(caracteres[6]);
            default -> false;
        };

    }

    /**
     * Calcula la diferencia de horas y minutos entre dos fechas en formato TIMESTAMP
     *
     * @param entrada fecha y hora de entrada en formato TIMESTAMP
     * @param salida  fecha y hora de salida en formato TIMESTAMP
     * @return duracion en formato Duration
     */
    public static Duration calcularHoras(Timestamp entrada, Timestamp salida) {
        LocalDateTime entradaDateTime = entrada.toLocalDateTime();
        LocalDateTime salidaDateTime = salida.toLocalDateTime();

        return Duration.between(entradaDateTime, salidaDateTime);
    }

    /**
     * Crea un string de la duracion en formato HH:MM
     *
     * @param d duracion a imprimir
     * @return String de la duracion
     */
    public static String impresionDuracion(Duration d) {
        long horas = d.toHours();
        long minutos = d.toMinutes() - (horas * 60);
        return String.format("%d:%02d", horas, minutos);
    }

    /**
     * Calcula la distancia entre dos GeoPositions
     *
     * @param pos1 GeoPosition 1
     * @param pos2 GeoPosition 2
     * @return distancia en metros
     */
    public static double calcularDistancia(GeoPosition pos1, GeoPosition pos2) {
        double R = 6371e3; // Radio de la Tierra en metros

        double lat1Rad = Math.toRadians(pos1.getLatitude());
        double lat2Rad = Math.toRadians(pos2.getLongitude());
        double deltaLat = Math.toRadians(pos2.getLatitude() - pos1.getLatitude());
        double deltaLon = Math.toRadians(pos2.getLongitude() - pos1.getLongitude());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    /**
     * Valida una hora ingresada con el formato HH:mm
     *
     * @param time hora a validar
     * @return true si es incorrecta
     */
    public static boolean validarHora(String time) {
        if (time.length() != 5) {
            return true;
        }
        char[] caracteres = time.toCharArray();
        return !Character.isDigit(caracteres[0]) || !Character.isDigit(caracteres[1]) || caracteres[2] != ':' || !Character.isDigit(caracteres[3]) || !Character.isDigit(caracteres[4]);
    }
}
