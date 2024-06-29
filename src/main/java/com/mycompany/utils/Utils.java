package com.mycompany.utils;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utils {

    /**
     * Valida una patente ingresada con el formato "LLNNNLL" (Patentes Argentinas 2015)
     *
     * @param patente patente a validar
     * @return
     */
    public static boolean validarPatente(String patente) {
        if (patente.length() != 7) {
            return false;
        }

        char[] caracteres = patente.toCharArray();

        if (!Character.isDigit(caracteres[0]) || !Character.isDigit(caracteres[1]) || !Character.isLetter(caracteres[2]) || !Character.isLetter(caracteres[3]) ||
                !Character.isLetter(caracteres[4]) || !Character.isDigit(caracteres[5]) || !Character.isDigit(caracteres[6])) {
            return false;
        }
        return true;
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

        Duration diferencia = Duration.between(entradaDateTime, salidaDateTime);

        return diferencia;
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
}
