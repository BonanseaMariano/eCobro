package com.mycompany.ecobro;

import com.mycompany.database.Database;
import com.mycompany.interfaces.DAOVehiculo;
import com.mycompany.models.Vehiculo;
import org.jxmapviewer.viewer.GeoPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOVehiculoImpl extends Database implements DAOVehiculo {

    /**
     * Registra un vehiculo en la base de datos
     *
     * @param vehiculo vehiculo a registrar
     * @throws Exception
     */
    @Override
    public void registrar(Vehiculo vehiculo) throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("INSERT INTO vehiculo (patente, horaEntrada, calle, uLat, uLon) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, vehiculo.getPatente());
            st.setTimestamp(2, new Timestamp(vehiculo.getHoraEntrada().getTime()));
            st.setString(3, vehiculo.getCalle());
            st.setDouble(4, vehiculo.getuLat());
            st.setDouble(5, vehiculo.getuLon());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * Modifica un vehiculo en la base de datos
     *
     * @param vehiculo vehiculo a modificar
     * @throws Exception
     */
    @Override
    public void modificar(Vehiculo vehiculo) throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE vehiculo SET horaEntrada = ?, calle = ?, uLat = ?, uLon = ? WHERE patente = ?");
            st.setTimestamp(1, new Timestamp(vehiculo.getHoraEntrada().getTime()));
            st.setString(2, vehiculo.getCalle());
            st.setDouble(3, vehiculo.getuLat());
            st.setDouble(4, vehiculo.getuLon());
            st.setString(5, vehiculo.getPatente());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * Elimina un vehiculo de la base de datos
     *
     * @param vehiculo vehiculo a eliminar
     * @throws Exception
     */
    @Override
    public void eliminar(Vehiculo vehiculo) throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM vehiculo WHERE patente = ?");
            st.setString(1, vehiculo.getPatente());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * Obtiene un vehiculo de la base de datos
     *
     * @param patente patente del vehiculo
     * @return vehiculo
     * @throws Exception
     */
    @Override
    public Vehiculo getVPatente(String patente) throws Exception {
        Vehiculo vehiculo = null;
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM vehiculo WHERE patente = ? LIMIT 1");
            st.setString(1, patente);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                vehiculo = new Vehiculo();
                vehiculo.setPatente(rs.getString("patente"));
                vehiculo.setHoraEntrada(rs.getTimestamp("horaEntrada"));
                vehiculo.setCalle(rs.getString("calle"));
                vehiculo.setuLat(rs.getDouble("uLat"));
                vehiculo.setuLon(rs.getDouble("uLon"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return null;
    }

    /**
     * Crea un mapa de patentes y sus Geopositions
     *
     * @return un hasmap de patentes y sus geopositions
     * @throws Exception
     */
    @Override
    public Map<String, GeoPosition> mapaUbicaciones() throws Exception {
        Map<String, GeoPosition> map = new HashMap<>();
        try {
            this.conectar();
            String query = "SELECT patente, uLat, uLon FROM vehiculo";
            PreparedStatement st = this.conexion.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String patente = rs.getString("patente");
                Double uLat = rs.getDouble("uLat");
                Double uLon = rs.getDouble("uLon");
                GeoPosition geoPosition = new GeoPosition(uLat, uLon);
                map.put(patente, geoPosition);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return map;
    }
}
