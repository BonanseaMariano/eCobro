package com.mycompany.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @org.junit.jupiter.api.Test
    void validarPatente() {
        assertTrue(Utils.validarPatente("22ABC22"));
        assertFalse(Utils.validarPatente("00=BC00"));
    }

    @org.junit.jupiter.api.Test
    void impresionDuracion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime unaHoraMas = ahora.plusHours(24).plusMinutes(90);

        Timestamp timestampAhora = Timestamp.valueOf(ahora);
        Timestamp timestampUnaHoraMas = Timestamp.valueOf(unaHoraMas);

        System.out.println(Utils.impresionDuracion(Utils.calcularHoras(timestampAhora, timestampUnaHoraMas)));
    }
}