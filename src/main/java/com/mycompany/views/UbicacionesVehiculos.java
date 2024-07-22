/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.views;

import com.mycompany.ecobro.DAOVehiculoImpl;
import com.mycompany.models.Vehiculo;
import com.mycompany.utils.Constants;
import com.mycompany.utils.Utils;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mariano
 */
public class UbicacionesVehiculos extends javax.swing.JPanel {

    private Vehiculo vehiculoSel = null;

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
        Map<String, Vehiculo> labeledPositions = null;
        try {
            labeledPositions = dao.mapaVehiculos();
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
        for (Map.Entry<String, Vehiculo> entry : labeledPositions.entrySet()) {
            waypoints.add(new LabeledWaypoint(entry.getValue().getGeoPosition(), entry.getKey()));
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
        } else {
            mapViewer.setAddressLocation(Constants.COORDENADAS_PMY);
        }

        // Establecer un nivel de zoom inicial
        mapViewer.setZoom(Constants.DFAULT_ZOOM);

        // Agregar controles de interacción
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        // Agregar listener para obtener coordenadas al hacer clic

        Map<String, Vehiculo> finalLabeledPositions = labeledPositions;
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GeoPosition geoPosition = mapViewer.convertPointToGeoPosition(e.getPoint());

                //Recorro todas las posiciones y veo cual es la mas cercana a la del clic (BAJO RENDIMIENTO CUANDO SON MUCHOS VEHICULOS POR COMPLEJIDAD LINEAL)
                for (Vehiculo v : finalLabeledPositions.values()) {
                    if (Utils.calcularDistancia(v.getGeoPosition(), geoPosition) < Constants.MIN_DIST_CLIC) {
                        tf_patente.setText(v.getPatente());
                        tf_tiempoEstacionado.setText(Utils.impresionDuracion(Utils.calcularHoras(v.getHoraEntrada(), new Timestamp(System.currentTimeMillis()))));
                        vehiculoSel = v;
                        break;
                    }
                }
            }
        });

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
        lb_titulo = new javax.swing.JLabel();
        lb_patente = new javax.swing.JLabel();
        tf_patente = new javax.swing.JTextField();
        lb_tiempoEstacionado = new javax.swing.JLabel();
        tf_tiempoEstacionado = new javax.swing.JTextField();
        bt_cobrar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 300));

        lb_titulo.setText("Seleccione un Vehiculo del mapa");

        lb_patente.setText("Patente:");

        tf_patente.setEditable(false);
        tf_patente.setBackground(new java.awt.Color(255, 255, 255));

        lb_tiempoEstacionado.setText("Tiempo Estacionado");

        tf_tiempoEstacionado.setEditable(false);
        tf_tiempoEstacionado.setBackground(new java.awt.Color(255, 255, 255));
        tf_tiempoEstacionado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_tiempoEstacionado.setBorder(null);

        bt_cobrar.setBackground(new java.awt.Color(255, 255, 255));
        bt_cobrar.setText("Cobrar");
        bt_cobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cobrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(sp_mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(lb_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(lb_patente)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(tf_patente))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(35, 35, 35)
                                                                        .addComponent(lb_tiempoEstacionado)))
                                                        .addComponent(tf_tiempoEstacionado, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(56, 56, 56)
                                                .addComponent(bt_cobrar)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp_mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb_titulo)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lb_patente)
                                        .addComponent(tf_patente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lb_tiempoEstacionado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_tiempoEstacionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_cobrar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bt_cobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cobrarActionPerformed
        if (vehiculoSel != null) {
            DAOVehiculoImpl dao = new DAOVehiculoImpl();
            // Obtener la hora de entrada del vehículo
            Timestamp horaEntrada = vehiculoSel.getHoraEntrada();
            Timestamp horaSalida = new Timestamp(System.currentTimeMillis());

            // Calcular la diferencia de tiempo en minutos
            long diffMinutes = (horaSalida.getTime() - horaEntrada.getTime()) / (60 * 1000);

            // Calcular el importe a abonar
            double importe = Constants.PRECIO_POR_MEDIAHORA * (diffMinutes / 30);

            // Mostrar el mensaje con el importe a abonar
            javax.swing.JOptionPane.showMessageDialog(this, "Importe a abonar: $" + importe + ".\n", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            //Borrar el vehiculo de la base de datos
            try {
                dao.eliminar(vehiculoSel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //Limpiar los campos
            tf_patente.setText("");
            tf_tiempoEstacionado.setText("");
            vehiculoSel = null;

            //Actualizar el mapa
            cargarMapa();
        }
    }//GEN-LAST:event_bt_cobrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cobrar;
    private javax.swing.JLabel lb_patente;
    private javax.swing.JLabel lb_tiempoEstacionado;
    private javax.swing.JLabel lb_titulo;
    private javax.swing.JScrollPane sp_mapa;
    private javax.swing.JTextField tf_patente;
    private javax.swing.JTextField tf_tiempoEstacionado;
    // End of variables declaration//GEN-END:variables
}
