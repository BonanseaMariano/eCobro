package com.mycompany.interfaces;

import com.mycompany.models.Vehiculo;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;
import java.util.Map;

public interface DAOVehiculo {

    public void registrar(Vehiculo vehiculo) throws Exception;

    public void modificar(Vehiculo vehiculo) throws Exception;

    public void eliminar(Vehiculo vehiculo) throws Exception;

    public Vehiculo getVPatente(String patente) throws Exception;

    public Map<String, GeoPosition> mapaUbicaciones() throws Exception;
}
