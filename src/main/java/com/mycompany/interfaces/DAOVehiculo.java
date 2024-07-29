package com.mycompany.interfaces;

import com.mycompany.models.Vehiculo;

import java.sql.SQLException;
import java.util.Map;

public interface DAOVehiculo {

    /**
     * Registra un vehiculo en la base de datos
     *
     * @param vehiculo vehiculo a registrar
     * @throws SQLException Si ocurre un error al registrar el vehiculo
     */
    void registrar(Vehiculo vehiculo) throws SQLException;

    /**
     * Modifica un vehiculo en la base de datos
     *
     * @param vehiculo     vehiculo a modificar
     * @param patenteNueva nueva patente
     * @throws SQLException Si ocurre un error al modificar el vehiculo
     */
    void modificar(Vehiculo vehiculo, String patenteNueva) throws SQLException;

    /**
     * Elimina un vehiculo de la base de datos
     *
     * @param vehiculo vehiculo a eliminar
     * @throws SQLException Si ocurre un error al eliminar el vehiculo
     */
    void eliminar(Vehiculo vehiculo) throws SQLException;

    /**
     * Crea un mapa de patentes y sus Vehiculos
     *
     * @return un hasmap de patentes y sus Vehiculos
     * @throws SQLException Si ocurre un error al obtener el mapa
     */
    Map<String, Vehiculo> mapaVehiculos() throws SQLException;

}
