package com.mycompany.views;

import com.mycompany.utils.Utils;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InteractiveMapTest {
    public static void main(String[] args) {
        // Crear una lista de coordenadas
        List<GeoPosition> geoPositions = Arrays.asList(
                Utils.convertirCoordenadasGM("-42.766575, -65.033028"), // Puerto Madryn
                Utils.convertirCoordenadasGM("-42.765573, -65.033664"), // Puerto Madryn
                Utils.convertirCoordenadasGM("-42.764612, -65.034289") // Puerto Madryn
        );

        // Crear el JScrollPane con el mapa
        JScrollPane mapScrollPane = InteractiveMap.createMap(geoPositions);

        // Crear el frame
        JFrame frame = new JFrame("Mapa Interactivo con JXMapViewer2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Agregar el JScrollPane al frame y mostrarlo
        frame.add(mapScrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}