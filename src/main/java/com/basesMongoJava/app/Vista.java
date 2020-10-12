package com.basesMongoJava.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

class Vista {
    public static void vista() {
        final JFrame framePrincipal = new JFrame("Menu principal");
        final JFrame frameEstadisticas = new JFrame("Visualización de estadísticas");

        JButton volver = new JButton("< Volver");
        volver.setBounds(250, 750, 500, 50);  //x axis, y axis, width, height
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameEstadisticas.setVisible(false);
                framePrincipal.setVisible(true);
            }
        });

        JButton generarYCargarEstadisticas = new JButton("Generar y cargar estadísticas");
        generarYCargarEstadisticas.setBounds(250, 350, 500, 50);
        generarYCargarEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OracleDatabase.updateSucursales();
                OracleDatabase.updateCiudades();
                OracleDatabase.updateDepartamento();
                OracleDatabase.updatePaises();
                OracleDatabase.updateVendedores();
                OracleDatabase.updateGremios();
                OracleDatabase.updateProductos();
                OracleDatabase.updateMarcas();
                JOptionPane.showMessageDialog(framePrincipal, "La información fue cargada correctamente");
            }
        });

        JLabel labelEstadisticas = new JLabel("Estadísticas:");
        labelEstadisticas.setBounds(120, 50, 300, 30);
        final JTextArea estadisticas = new JTextArea();
        //estadisticas.setBounds();
        estadisticas.setEditable(false);
        JScrollPane scroll = new JScrollPane(estadisticas,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(100, 85, 800,  600);

        frameEstadisticas.add(volver);
        frameEstadisticas.add(labelEstadisticas);
        frameEstadisticas.add(scroll);

        JButton visualizarEstadisticas = new JButton("Visualizar estadísticas");
        visualizarEstadisticas.setBounds(250, 600, 500, 50);
        visualizarEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder texto = new StringBuilder();
                String[] colecciones = new String[]{"vendedor","gremio","sucursal","ciudad","departamento"};
                for (String coleccion : colecciones){
                    texto.append(Estadisticas.getOtraCosa(coleccion, MongoDatabase.findAll(coleccion)));
                }
                texto.append(Estadisticas.getPaises(MongoDatabase.findAll("pais")));
                texto.append(Estadisticas.getProductos(MongoDatabase.findAll("producto")));
                texto.append(Estadisticas.getMarcas(MongoDatabase.findAll("marca")));
                estadisticas.setText(texto.toString());
                framePrincipal.setVisible(false);
                frameEstadisticas.setVisible(true);
            }
        });

        framePrincipal.add(generarYCargarEstadisticas);
        framePrincipal.add(visualizarEstadisticas);

        framePrincipal.setSize(1000, 1000);  //400 width and 500 height
        frameEstadisticas.setSize(1000, 1000);  //400 width and 500 height

        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEstadisticas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        framePrincipal.setLayout(null);  //using no layout managers
        frameEstadisticas.setLayout(null);  //using no layout managers

        framePrincipal.setVisible(true);  //making the frame visible
    }
}
