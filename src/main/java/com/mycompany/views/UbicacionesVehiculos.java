/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.views;

import com.mycompany.ecobro.DAOVehiculoImpl;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mariano
 */
public class UbicacionesVehiculos extends javax.swing.JPanel {

    private class LabeledWaypoint extends DefaultWaypoint {
        private final String label;

        public LabeledWaypoint(GeoPosition position, String label) {
            super(position);
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private class LabeledWaypointRenderer implements WaypointRenderer<LabeledWaypoint> {
        @Override
        public void paintWaypoint(Graphics2D g, JXMapViewer map, LabeledWaypoint wp) {
            g.setColor(Color.RED);
            Point2D point = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());

            int x = (int) point.getX();
            int y = (int) point.getY();

            g.fillOval(x - 5, y - 5, 10, 10);

            g.setColor(Color.BLACK);
            g.drawString(wp.getLabel(), x + 10, y);
        }
    }

    /**
     * Creates new form UbicacionesVehiculos
     */
    public UbicacionesVehiculos() {
        initComponents();
        cargarMapa();
    }

    private void cargarMapa() {
        DAOVehiculoImpl dao = new DAOVehiculoImpl();
        Map<String, GeoPosition> labeledPositions = null;
        try {
            labeledPositions = dao.mapaUbicaciones();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Crear el mapa
        JXMapViewer mapViewer = new JXMapViewer();

        // Configurar el TileFactory con OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        TileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Crear un conjunto de waypoints etiquetados
        Set<LabeledWaypoint> waypoints = new HashSet<>();
        for (Map.Entry<String, GeoPosition> entry : labeledPositions.entrySet()) {
            waypoints.add(new LabeledWaypoint(entry.getValue(), entry.getKey()));
        }

        // Crear un WaypointPainter para dibujar los marcadores
        WaypointPainter<LabeledWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new LabeledWaypointRenderer());

        // Configurar el mapViewer con el WaypointPainter
        mapViewer.setOverlayPainter(waypointPainter);

        // Centrar el mapa en la primera coordenada, si hay alguna
        if (!waypoints.isEmpty()) {
            mapViewer.setAddressLocation(waypoints.iterator().next().getPosition());
        }

        // Establecer un nivel de zoom inicial
        mapViewer.setZoom(3);

        // Agregar controles de interacción
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        // Crear y devolver el JScrollPane con el mapViewer
        sp_mapa.setViewportView(mapViewer);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp_mapa = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp_mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp_mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane sp_mapa;
    // End of variables declaration//GEN-END:variables
}
