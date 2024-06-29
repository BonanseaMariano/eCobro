package com.mycompany.interfaces;

import com.mycompany.models.Vehiculo;

import java.util.List;

public interface DAOVehiculo {

    public void registrar(Vehiculo vehiculo) throws Exception;

    public void modificar(Vehiculo vehiculo) throws Exception;

    public void eliminar(Vehiculo vehiculo) throws Exception;

    public Vehiculo getVPatente(String patente) throws Exception;

    public List<Vehiculo> listar(String calle) throws Exception;
}
