package com.mycompany.ecobro;

import com.mycompany.database.Database;
import com.mycompany.interfaces.DAOVehiculo;
import com.mycompany.models.Vehiculo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DAOVehiculoImpl extends Database implements DAOVehiculo {

    @Override
    public void registrar(Vehiculo vehiculo) throws SQLException {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("INSERT INTO vehiculo (patente, horaEntrada, calle, uLat, uLon) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, vehiculo.getPatente());
            st.setString(2, new SimpleDateFormat("HH:mm").format(vehiculo.getHoraEntrada()));
            st.setString(3, vehiculo.getCalle());
            st.setDouble(4, vehiculo.getuLat());
            st.setDouble(5, vehiculo.getuLon());
            st.executeUpdate();
            st.close();
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Vehiculo vehiculo, String patenteNueva) throws SQLException {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE vehiculo SET patente = ?, horaEntrada = ?, calle = ?, uLat = ?, uLon = ? WHERE patente = ?");
            st.setString(1, patenteNueva);
            st.setString(2, new SimpleDateFormat("HH:mm").format(vehiculo.getHoraEntrada()));
            st.setString(3, vehiculo.getCalle());
            st.setDouble(4, vehiculo.getuLat());
            st.setDouble(5, vehiculo.getuLon());
            st.setString(6, vehiculo.getPatente());
            st.executeUpdate();
            st.close();
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(Vehiculo vehiculo) throws SQLException {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM vehiculo WHERE patente = ?");
            st.setString(1, vehiculo.getPatente());
            st.executeUpdate();
            st.close();
        } finally {
            this.cerrar();
        }
    }

    @Override
    public Map<String, Vehiculo> mapaVehiculos() throws SQLException {
        Map<String, Vehiculo> map = new HashMap<>();
        try {
            this.conectar();
            String query = "SELECT patente, horaEntrada, uLat, uLon, calle FROM vehiculo";
            PreparedStatement st = this.conexion.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setPatente(rs.getString("patente"));
                String horaEntradaStr = rs.getString("horaEntrada");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date horaEntradaDate = sdf.parse(horaEntradaStr);
                vehiculo.setHoraEntrada(new Timestamp(horaEntradaDate.getTime()));
                vehiculo.setuLat(rs.getDouble("uLat"));
                vehiculo.setuLon(rs.getDouble("uLon"));
                vehiculo.setCalle(rs.getString("calle"));
                map.put(vehiculo.getPatente(), vehiculo);
            }
            rs.close();
            st.close();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            this.cerrar();
        }
        return map;
    }

}
