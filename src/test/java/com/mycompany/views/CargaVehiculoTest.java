package com.mycompany.views;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CargaVehiculoTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CargaVehiculo cargaVehiculo = new CargaVehiculo();
        frame.add(cargaVehiculo);
        frame.pack();
        frame.setVisible(true);
    }

}