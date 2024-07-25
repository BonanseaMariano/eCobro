/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.views;

import com.mycompany.ecobro.DAOVehiculoImpl;
import com.mycompany.interfaces.DAOVehiculo;
import com.mycompany.models.Vehiculo;
import com.mycompany.utils.Constants;
import com.mycompany.utils.Utils;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * @author Mariano
 */
public class CargaVehiculo extends javax.swing.JPanel {

    /**
     * Creates new form CargaVehiculo
     */
    public CargaVehiculo() {
        initComponents();
        actualizarHora();
        cargarMapa();
        initStyles();
    }

    /**
     * Inicializa los estilos del panel con FlatLaf
     */
    private void initStyles() {
        lbl_titulo.putClientProperty("FlatLaf.style", "font: bold $h1.regular.font");
        lbl_patente.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        lbl_calle.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        lbl_hora.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        lbl_lat.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        lbl_long.putClientProperty("FlatLaf.style", "font: 12 $light.font");
        bt_cargar.putClientProperty("FlatLaf.style", "font: 14 $light.font");

        lbl_titulo.setForeground(Color.BLACK);
        lbl_patente.setForeground(Color.BLACK);
        lbl_calle.setForeground(Color.BLACK);
        lbl_hora.setForeground(Color.BLACK);
        lbl_lat.setForeground(Color.BLACK);
        lbl_long.setForeground(Color.BLACK);
        bt_cargar.setForeground(Color.BLACK);
    }

    /**
     * Inicializa el mapa con un listener para obtener las coordenadas
     */
    private void cargarMapa() {
        // Crear el mapa
        JXMapViewer mapViewer = getJxMapViewer();

        // Agregarle los controles de interacción al mapa
        agregarControlesMapa(mapViewer);

        // Agregar el mapa al JScrollPane
        sp_mapa.setViewportView(mapViewer);
    }

    /**
     * Crea el mapa con OpenStreetMap
     *
     * @return el mapa en forma de JXMapViewer
     */
    private JXMapViewer getJxMapViewer() {
        JXMapViewer mapViewer = new JXMapViewer();

        // Configurar el TileFactory con OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        TileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Cofigurar el mapa para que aparezca centrado en el centro de Puerto Madryn
        mapViewer.setAddressLocation(Constants.COORDENADAS_PMY);

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
                tf_lat.setText(String.valueOf(geoPosition.getLatitude()));
                tf_long.setText(String.valueOf(geoPosition.getLongitude()));
            }
        });
    }

    private void actualizarHora() {
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Formatear la hora y minutos en el formato "HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = now.format(formatter);

        // Cargar la hora y minutos en el JTextField
        tf_hora.setText(horaFormateada);
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
        bg_contenido = new javax.swing.JPanel();
        lbl_titulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_patente = new javax.swing.JLabel();
        tf_patente = new javax.swing.JTextField();
        lbl_hora = new javax.swing.JLabel();
        lbl_calle = new javax.swing.JLabel();
        tf_calle = new javax.swing.JTextField();
        lbl_lat = new javax.swing.JLabel();
        tf_lat = new javax.swing.JTextField();
        lbl_long = new javax.swing.JLabel();
        tf_long = new javax.swing.JTextField();
        bt_cargar = new javax.swing.JButton();
        tf_hora = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 204, 204));

        sp_mapa.setBackground(new java.awt.Color(255, 255, 255));
        sp_mapa.setForeground(new java.awt.Color(255, 255, 255));
        sp_mapa.setPreferredSize(new java.awt.Dimension(800, 600));

        bg_contenido.setBackground(new java.awt.Color(255, 255, 255));

        lbl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_titulo.setText("Cargar Vehiculo");
        lbl_titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbl_patente.setText("Patente:");

        tf_patente.setBackground(new java.awt.Color(255, 255, 255));

        lbl_hora.setText("Hora:");

        lbl_calle.setText("Calle:");

        tf_calle.setBackground(new java.awt.Color(255, 255, 255));

        lbl_lat.setText("Latitud:");

        tf_lat.setEditable(false);
        tf_lat.setBackground(new java.awt.Color(255, 255, 255));

        lbl_long.setText("Longitud");

        tf_long.setEditable(false);
        tf_long.setBackground(new java.awt.Color(255, 255, 255));

        bt_cargar.setBackground(new java.awt.Color(255, 255, 255));
        bt_cargar.setText("Cargar");
        bt_cargar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bt_cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cargarActionPerformed();
            }
        });

        tf_hora.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bg_contenidoLayout = new javax.swing.GroupLayout(bg_contenido);
        bg_contenido.setLayout(bg_contenidoLayout);
        bg_contenidoLayout.setHorizontalGroup(
                bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bg_contenidoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bt_cargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addGroup(bg_contenidoLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lbl_patente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbl_hora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbl_calle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbl_lat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbl_long, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(tf_patente, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                                        .addComponent(tf_calle, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                                        .addComponent(tf_lat, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                                        .addComponent(tf_long, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                                        .addComponent(tf_hora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        bg_contenidoLayout.setVerticalGroup(
                bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bg_contenidoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tf_patente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_patente))
                                .addGap(18, 18, 18)
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_hora)
                                        .addComponent(tf_hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_calle)
                                        .addComponent(tf_calle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_lat)
                                        .addComponent(tf_lat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(bg_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_long)
                                        .addComponent(tf_long, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(bt_cargar)
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(sp_mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bg_contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp_mapa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bg_contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Pulsacion del boton para cargar vehiculo
     */
    private void bt_cargarActionPerformed() {
        //Validacion de campos
        if (tf_patente.getText().isEmpty() || tf_lat.getText().isEmpty() || tf_long.getText().isEmpty() || tf_hora.getText().isEmpty() || tf_calle.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe llenar todos los campos. \n", "AVISO", javax.swing.JOptionPane.ERROR_MESSAGE);
            tf_patente.requestFocus();
            return;
        } else if (!Utils.validarPatente(tf_patente.getText())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Patente Invalida, debe ser formato \"LLNNNLL\" o \"LLLNNN\" \n", "AVISO", javax.swing.JOptionPane.ERROR_MESSAGE);
            tf_patente.requestFocus();
            return;
        } else if (!Utils.validarHora(tf_hora.getText())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hora Invalida, debe ser formato \"HH:MM\" \n", "AVISO", javax.swing.JOptionPane.ERROR_MESSAGE);
            tf_hora.requestFocus();
            return;
        }

        //Asignacion de valores
        String patente = tf_patente.getText().toUpperCase();
        Timestamp hora;
        Double uLat = Double.parseDouble(tf_lat.getText());
        Double uLon = Double.parseDouble(tf_long.getText());
        String calle = tf_calle.getText();

        //Obtener fecha actual
        LocalDate currentDate = LocalDate.now();

        //Obtener hora y minutos del textField
        String timeString = tf_hora.getText();
        LocalTime time = LocalTime.parse(timeString);

        //Combinar fecha y hora
        LocalDateTime dateTime = LocalDateTime.of(currentDate, time);

        hora = new Timestamp(Timestamp.valueOf(dateTime).getTime());

        //Registro de vehiculo
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(patente);
        vehiculo.setHoraEntrada(hora);
        vehiculo.setuLat(uLat);
        vehiculo.setuLon(uLon);
        vehiculo.setCalle(calle);

        try {
            DAOVehiculo dao = new DAOVehiculoImpl();
            dao.registrar(vehiculo);
            javax.swing.JOptionPane.showMessageDialog(this, "Vehiculo registrado exitosamente.\n", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            //Limpiar campos
            tf_patente.setText("");
            actualizarHora();
            tf_lat.setText("");
            tf_long.setText("");
            tf_calle.setText("");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar el vehiculo. \n", "AVISO", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_bt_cargarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg_contenido;
    private javax.swing.JButton bt_cargar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_calle;
    private javax.swing.JLabel lbl_hora;
    private javax.swing.JLabel lbl_lat;
    private javax.swing.JLabel lbl_long;
    private javax.swing.JLabel lbl_patente;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JScrollPane sp_mapa;
    private javax.swing.JTextField tf_calle;
    private javax.swing.JTextField tf_hora;
    private javax.swing.JTextField tf_lat;
    private javax.swing.JTextField tf_long;
    private javax.swing.JTextField tf_patente;
    // End of variables declaration//GEN-END:variables
}
