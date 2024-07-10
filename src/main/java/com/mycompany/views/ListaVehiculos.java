/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.views;

import com.mycompany.ecobro.DAOVehiculoImpl;
import com.mycompany.models.Vehiculo;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mariano
 */
public class ListaVehiculos extends javax.swing.JPanel {

    private static Map<Integer, Vehiculo> mapaPatentes = new HashMap<>();

    /**
     * Creates new form ListaVehiculos
     */
    public ListaVehiculos() {
        initComponents();
        initcontent();
    }

    private void initcontent() {
        DAOVehiculoImpl dao = new DAOVehiculoImpl();

        try {
            // Obtener la lista de vehículos
            List<Vehiculo> vehiculos = dao.listarVehiculos();

            // Crear un DefaultTableModel con las columnas "patente", "calle" y "Hora Estacionamiento"
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Patente");
            model.addColumn("Calle");
            model.addColumn("Hora Estacionamiento");

            //Contador para el mapa de patentes
            int temp = 0;

            // Agregar los datos de los vehículos del dia al modelo
            for (Vehiculo vehiculo : vehiculos) {
                LocalDateTime entradaDateTime = LocalDateTime.ofInstant(vehiculo.getHoraEntrada().toInstant(), ZoneId.systemDefault());
                LocalTime horaEntrada = entradaDateTime.toLocalTime();
                String horaFormateada = horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm"));
                Object[] rowData = {vehiculo.getPatente(), vehiculo.getCalle(), horaFormateada};
                model.addRow(rowData);

                //inicializo mapa patentes para actualizaciones de la tabla
                mapaPatentes.put(temp, vehiculo);
                temp++;
            }

            // Agregar un listener para detectar cambios en los datos de la tabla
            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    tablaActualizada(row, column, model);
                }
            });

            // Asignar el modelo al JTable
            tb_vehiculos.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tablaActualizada(int row, int column, DefaultTableModel model) {
        Object value = model.getValueAt(row, column);
        DAOVehiculoImpl dao = new DAOVehiculoImpl();

        Vehiculo selectedVehiculo = new Vehiculo();
        selectedVehiculo.setPatente(mapaPatentes.get(row).getPatente());
        selectedVehiculo.setHoraEntrada(mapaPatentes.get(row).getHoraEntrada());
        selectedVehiculo.setCalle(mapaPatentes.get(row).getCalle());
        selectedVehiculo.setuLat(mapaPatentes.get(row).getuLat());
        selectedVehiculo.setuLon(mapaPatentes.get(row).getuLon());

        switch (column) {
            case 0: //Modificacion patente
                try {
                    dao.modificar(selectedVehiculo, value.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1: //Modificacion Calle
                try {
                    selectedVehiculo.setCalle(value.toString());
                    dao.modificar(selectedVehiculo, selectedVehiculo.getPatente());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 2: //Modificacion Hora
                try {
                    Date date = new Date();
                    String time = value.toString();
                    String[] hym = time.split(":");
                    int h = Integer.parseInt(hym[0]);
                    int m = Integer.parseInt(hym[1]);

                    Timestamp ts = new Timestamp(date.getYear(), date.getMonth(), date.getDate(), h, m, 0, 0);

                    selectedVehiculo.setHoraEntrada(ts);
                    dao.modificar(selectedVehiculo, selectedVehiculo.getPatente());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp_vehiculos = new javax.swing.JScrollPane();
        tb_vehiculos = new javax.swing.JTable();
        bt_eliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 300));

        tb_vehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_vehiculos.setShowGrid(false);
        sp_vehiculos.setViewportView(tb_vehiculos);

        bt_eliminar.setText("Eliminar");
        bt_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp_vehiculos)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(529, 529, 529)
                .addComponent(bt_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(498, 498, 498))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sp_vehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_eliminar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bt_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_eliminarActionPerformed
        int indSel = tb_vehiculos.getSelectedRow();
        if (indSel != -1) {
            DAOVehiculoImpl dao = new DAOVehiculoImpl();
            try {
                dao.eliminar(mapaPatentes.get(indSel));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            DefaultTableModel model = (DefaultTableModel) tb_vehiculos.getModel();
            model.removeRow(indSel);
            mapaPatentes.remove(indSel);
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_bt_eliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_eliminar;
    private javax.swing.JScrollPane sp_vehiculos;
    private javax.swing.JTable tb_vehiculos;
    // End of variables declaration//GEN-END:variables
}
