package com.mycompany.views;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InteractiveMap {
    /**
     * Crea un JScrollPane con un JXMapViewer que contiene un mapa con waypoints para cada GeoPosition en la lista proporcionada.
     *
     * @param geoPositions una lista de GeoPositions para crear waypoints en el mapa
     * @return un JScrollPane que contiene el JXMapViewer con el mapa y los waypoints
     */
    public static JScrollPane createMap(List<GeoPosition> geoPositions) {
        // Crear el mapa
        JXMapViewer mapViewer = new JXMapViewer();

        // Configurar el TileFactory con OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        TileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Crear una lista de waypoints
        Set<DefaultWaypoint> waypoints = new HashSet<>();
        for (GeoPosition geoPosition : geoPositions) {
            waypoints.add(new DefaultWaypoint(geoPosition));
        }

        // Crear un WaypointPainter para dibujar los marcadores
        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        // Configurar el mapViewer con el WaypointPainter
        mapViewer.setOverlayPainter(waypointPainter);

        // Centrar el mapa en la primera coordenada, si hay alguna
        if (!geoPositions.isEmpty()) {
            mapViewer.setAddressLocation(geoPositions.get(0));
        }

        // Establecer un nivel de zoom inicial
        mapViewer.setZoom(4);

        // Agregar controles de interacción
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        // Agregar listener para obtener coordenadas al hacer clic
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GeoPosition geoPosition = mapViewer.convertPointToGeoPosition(e.getPoint());
                System.out.println("Coordenadas: " + geoPosition);
            }
        });


        // Crear y devolver el JScrollPane con el mapViewer
        return new JScrollPane(mapViewer);
    }
}
