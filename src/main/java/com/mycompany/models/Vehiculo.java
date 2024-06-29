package com.mycompany.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Vehiculo {
    private String patente;
    private Timestamp horaEntrada;
    private Double uLat;
    private Double uLon;
    private String calle;

    public String getPatente() {
        return patente;
    }

    public Timestamp getHoraEntrada() {
        return horaEntrada;
    }

    public Double getuLat() {
        return uLat;
    }

    public Double getuLon() {
        return uLon;
    }

    public String getCalle() {
        return calle;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setHoraEntrada(Timestamp horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setuLat(Double uLat) {
        this.uLat = uLat;
    }

    public void setuLon(Double uLon) {
        this.uLon = uLon;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patente);
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "patente='" + patente + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", calle='" + calle + '\'' +
                '}';
    }
}
