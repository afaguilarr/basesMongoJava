package com.basesMongoJava.ruleOneApp;

import com.basesMongoJava.common.Rule1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Vista {
    public static void vista() {
        final JFrame frameReglaUno = new JFrame("Visualización de estadísticas");

        JLabel labelT1 = new JLabel("Ingrese el nombre de la tabla 1 (T1):");
        labelT1.setBounds(120, 50, 800, 30);
        final JTextField T1 = new JTextField();
        T1.setBounds(100, 85, 800, 40);

        JLabel labelT2 = new JLabel("Ingrese el nombre de la tabla 2 (T2):");
        labelT2.setBounds(120, 150, 800, 30);
        final JTextField T2 = new JTextField();
        T2.setBounds(100, 185, 800, 40);

        JLabel labelT1Fields = new JLabel("Ingrese los atributos de la tabla 1 (T1) separados por coma (ejemplo: atr1,atr2,atr3):");
        labelT1Fields.setBounds(120, 250, 800, 30);
        final JTextField T1Fields = new JTextField();
        T1Fields.setBounds(100, 285, 800, 40);

        JLabel labelT2Fields = new JLabel("Ingrese los atributos de la tabla 2 (T2) separados por coma (ejemplo: atr1,atr2,atr3):");
        labelT2Fields.setBounds(120, 350, 800, 30);
        final JTextField T2Fields = new JTextField();
        T2Fields.setBounds(100, 385, 800, 40);

        JLabel S1Label = new JLabel("Ingrese las consultas S1 separadas por un salto de línea (enter):");
        S1Label.setBounds(120, 450, 800, 30);
        final JTextArea S1 = new JTextArea();
        S1.setBounds(100, 485, 800, 100);

        JLabel S2Label = new JLabel("Ingrese las consultas S2 separadas por un salto de línea (enter):");
        S2Label.setBounds(120, 610, 800, 30);
        final JTextArea S2 = new JTextArea();
        S2.setBounds(100, 645, 800, 100);

        final JLabel respuesta = new JLabel("Q' = ?");
        respuesta.setBounds(340, 775, 2000, 30);

        JButton generar = new JButton("Generar");
        generar.setBounds(120, 775, 200, 50);  //x axis, y axis, width, height
        generar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String t1 = T1.getText();
                String t2 = T2.getText();

                Set<String> t1Attributes = new HashSet<>(
                        Arrays.asList(T1Fields.getText().split(","))
                );
                Set<String> t2Attributes = new HashSet<>(
                        Arrays.asList(T2Fields.getText().split(","))
                );

                ArrayList<String> s2Queries = new ArrayList<>(
                        Arrays.asList(S2.getText().split("\n"))
                );
                ArrayList<String> s1Queries = new ArrayList<>(
                        Arrays.asList(S1.getText().split("\n"))
                );

                Rule1 rule1 = new Rule1(t1, t2, t1Attributes, t2Attributes, s1Queries,s2Queries);

                String newcollection = rule1.generateNewCollection();
                respuesta.setText(newcollection);
            }
        });

        frameReglaUno.add(labelT1);
        frameReglaUno.add(T1);
        frameReglaUno.add(labelT1Fields);
        frameReglaUno.add(T1Fields);
        frameReglaUno.add(labelT2);
        frameReglaUno.add(T2);
        frameReglaUno.add(labelT2Fields);
        frameReglaUno.add(T2Fields);
        frameReglaUno.add(S2Label);
        frameReglaUno.add(S2);
        frameReglaUno.add(S1Label);
        frameReglaUno.add(S1);
        frameReglaUno.add(generar);
        frameReglaUno.add(respuesta);

        frameReglaUno.setSize(1000, 1000);  //400 width and 500 height
        frameReglaUno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameReglaUno.setLayout(null);  //using no layout managers
        frameReglaUno.setVisible(true);  //making the frame visible
    }
}
