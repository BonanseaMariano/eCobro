package com.mycompany.ecobro;

import com.mycompany.database.Database;
import com.mycompany.interfaces.DAOVehiculo;
import com.mycompany.models.Vehiculo;
import org.jxmapviewer.viewer.GeoPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @param vehiculo     vehiculo a modificar
     * @param patenteNueva nueva patente
     * @throws Exception
     */
    @Override
    public void modificar(Vehiculo vehiculo, String patenteNueva) throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE vehiculo SET patente = ?, horaEntrada = ?, calle = ?, uLat = ?, uLon = ? WHERE patente = ?");
            st.setString(1, patenteNueva);
            st.setTimestamp(2, new Timestamp(vehiculo.getHoraEntrada().getTime()));
            st.setString(3, vehiculo.getCalle());
            st.setDouble(4, vehiculo.getuLat());
            st.setDouble(5, vehiculo.getuLon());
            st.setString(6, vehiculo.getPatente());
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
    public Map<String, Vehiculo> mapaVehiculos() throws Exception {
        Map<String, Vehiculo> map = new HashMap<>();
        try {
            this.conectar();
            String query = "SELECT patente, horaEntrada, uLat, uLon, calle FROM vehiculo";
            PreparedStatement st = this.conexion.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setPatente(rs.getString("patente"));
                vehiculo.setHoraEntrada(rs.getTimestamp("horaEntrada"));
                vehiculo.setuLat(rs.getDouble("uLat"));
                vehiculo.setuLon(rs.getDouble("uLon"));
                vehiculo.setCalle(rs.getString("calle"));
                map.put(vehiculo.getPatente(), vehiculo);
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

    /**
     * Obtiene todos los vehiculos de la base de datos
     *
     * @return lista de vehiculos
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Vehiculo> listarVehiculos() throws SQLException, ClassNotFoundException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM vehiculo";
            PreparedStatement st = this.conexion.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setPatente(rs.getString("patente"));
                vehiculo.setHoraEntrada(rs.getTimestamp("horaEntrada"));
                vehiculo.setCalle(rs.getString("calle"));
                vehiculo.setuLat(rs.getDouble("uLat"));
                vehiculo.setuLon(rs.getDouble("uLon"));
                vehiculos.add(vehiculo);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return vehiculos;
    }
}
