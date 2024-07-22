package com.mycompany.interfaces;

import com.mycompany.models.Vehiculo;

import java.util.List;
import java.util.Map;

public interface DAOVehiculo {

    void registrar(Vehiculo vehiculo) throws Exception;

    void modificar(Vehiculo vehiculo, String patenteNueva) throws Exception;

    void eliminar(Vehiculo vehiculo) throws Exception;

    Map<String, Vehiculo> mapaVehiculos() throws Exception;

    List<Vehiculo> listarVehiculos() throws Exception;
}
