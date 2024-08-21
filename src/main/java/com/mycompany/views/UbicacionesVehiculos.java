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

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mariano
 */
public class UbicacionesVehiculos extends javax.swing.JPanel {

    private static Map<String, Vehiculo> labeledPositions;

    private static class LabeledWaypoint extends DefaultWaypoint {
        private final String label;

        public LabeledWaypoint(GeoPosition position, String label) {
            super(position);
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private static class LabeledWaypointRenderer implements WaypointRenderer<LabeledWaypoint> {
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
        initStyles();
        initContent();
        cargarMapa();
    }

    /**
     * Inicializa contenido del panel (los datos de los vehiculos)
     */
    private void initContent() {
        DAOVehiculoImpl dao = new DAOVehiculoImpl();
        try {
            labeledPositions = dao.mapaVehiculos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        actualizarCbPatentes();
    }

    /**
     * Actualiza el JComboBox con las patentes
     */
    private void actualizarCbPatentes() {
        if (!labeledPositions.isEmpty()) {
            // El mapa no está vacío, agregar las coordenadas al JComboBox
            Set<String> patentes = labeledPositions.keySet();
            String[] patentesArray = patentes.toArray(new String[0]);
            cb_patente.setModel(new DefaultComboBoxModel<>(patentesArray));
        }
    }

    /**
     * Inicializa el mapa con un listener para obtener las coordenadas
     */
    private void cargarMapa() {
        // Crear el mapa
        JXMapViewer mapViewer = getJxMapViewer();

        //Agregarle los controles de interaccion al mapa
        agregarControlesMapa(mapViewer);

        // Crear y devolver el JScrollPane con el mapViewer
        sp_mapa.setViewportView(mapViewer);
    }

    /**
     * Crea el mapa con OpenStreetMap
     *
     * @return el mapa en forma de JXMapViewer con las patentetes de los vehiculos
     */
    private JXMapViewer getJxMapViewer() {
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

        return mapViewer;
    }

    /**
     * Agrega los controles de interacción al mapa
     *
     * @param mapViewer el mapa en formato JXMapViewer
     */
    private void agregarControlesMapa(JXMapViewer mapViewer) {
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
                //Recorro todas las posiciones y veo cual es la mas cercana a la del clic (BAJO RENDIMIENTO CUANDO SON MUCHOS VEHICULOS POR COMPLEJIDAD LINEAL)
                for (Vehiculo v : labeledPositions.values()) {
                    if (Utils.calcularDistancia(v.getGeoPosition(), geoPosition) < Constants.MIN_DIST_CLIC) {
                        cb_patente.setSelectedItem(v.getPatente());
                        tf_tiempoEstacionado.setText(Utils.impresionDuracion(Utils.calcularHoras(v.getHoraEntrada(), new Timestamp(System.currentTimeMillis()))));
                        break;
                    }
                }
            }
        });
    }

    /**
     * Initializes the style of the panel
     */
    private void initStyles() {
        lb_titulo.putClientProperty("FlatLaf.style", "font: bold $h1.regular.font");
        lb_patente.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        cb_patente.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        lb_tiempoEstacionado.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        tf_tiempoEstacionado.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        bt_cobrar.putClientProperty("FlatLaf.style", "font: 12 $light.font");

        lb_titulo.setForeground(Color.BLACK);
        lb_patente.setForeground(Color.BLACK);
        cb_patente.setForeground(Color.BLACK);
        lb_tiempoEstacionado.setForeground(Color.BLACK);
        tf_tiempoEstacionado.setForeground(Color.BLACK);
        bt_cobrar.setForeground(Color.BLACK);
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
        lb_tiempoEstacionado = new javax.swing.JLabel();
        tf_tiempoEstacionado = new javax.swing.JTextField();
        bt_cobrar = new javax.swing.JButton();
        cb_patente = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 300));

        lb_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_titulo.setText("Seleccione un Vehiculo del mapa");

        lb_patente.setText("Patente:");

        lb_tiempoEstacionado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_tiempoEstacionado.setText("Tiempo Estacionado");

        tf_tiempoEstacionado.setEditable(false);
        tf_tiempoEstacionado.setBackground(new java.awt.Color(255, 255, 255));
        tf_tiempoEstacionado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_tiempoEstacionado.setBorder(null);
        tf_tiempoEstacionado.setFocusable(false);

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_tiempoEstacionado, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                        .addComponent(lb_tiempoEstacionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lb_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lb_patente)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cb_patente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(bt_cobrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                        .addComponent(cb_patente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lb_tiempoEstacionado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_tiempoEstacionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_cobrar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Funcionalidad del boton para cobrar el tiempo de estacionamiento
     *
     * @param evt evento de pulsar el boton
     */
    private void bt_cobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cobrarActionPerformed
        if (cb_patente.getSelectedIndex() != -1) {
            DAOVehiculoImpl dao = new DAOVehiculoImpl();

            //Obtengo el vehiculo correspondiente a la patente
            Vehiculo vehiculoSel = labeledPositions.get(Objects.requireNonNull(cb_patente.getSelectedItem()).toString());

            // Obtener la hora de entrada del vehículo
            Timestamp horaEntrada = vehiculoSel.getHoraEntrada();
            Timestamp horaSalida = new Timestamp(System.currentTimeMillis());

            // Calcular la diferencia de tiempo en minutos
            //TODO revisar esto que el calculo es incorrecto
            long diffMinutes = (horaSalida.getTime() - horaEntrada.getTime()) / (60 * 1000);

            // Calcular el importe a abonar
            double importe = Constants.PRECIO_POR_MEDIAHORA * (diffMinutes / 30);

            // Mostrar el mensaje con el importe a abonar
            javax.swing.JOptionPane.showMessageDialog(this, "Importe a abonar: $" + importe + ".\n", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            //Borrar el vehiculo de la base de datos
            try {
                dao.eliminar(vehiculoSel);
            } catch (Exception e) {
                throw new RuntimeException("Error al borrar vehiculo: " + e.getMessage());
            }

            //Saco el vehiculo del mapa de patentes
            labeledPositions.remove(vehiculoSel.getPatente());

            //Limpiar los campos
            cb_patente.setSelectedIndex(-1);
            tf_tiempoEstacionado.setText("");

            //Actualizar el mapa
            cargarMapa();

            //Actualizar el cb
            actualizarCbPatentes();
        }
    }//GEN-LAST:event_bt_cobrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cobrar;
    private javax.swing.JComboBox<String> cb_patente;
    private javax.swing.JLabel lb_patente;
    private javax.swing.JLabel lb_tiempoEstacionado;
    private javax.swing.JLabel lb_titulo;
    private javax.swing.JScrollPane sp_mapa;
    private javax.swing.JTextField tf_tiempoEstacionado;
    // End of variables declaration//GEN-END:variables
}
