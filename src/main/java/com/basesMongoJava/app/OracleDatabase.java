package main.java.com.basesMongoJava.app;

import java.sql.*;
import java.util.ArrayList;

public class OracleDatabase {
    private static Connection connection;
    private static Statement sentencia;
    private static ResultSet resultado;

    private static void startConnection() {
        System.out.println("\nConexión a la base de datos...");

        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            System.out.println(e);
            return;
        }
        try { // Se establece la conexión con la base de datos
            connection = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521:xe", "gato", "gato");
            sentencia = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("No hay conexión con la base de datos.");
            System.out.println(e);
        }
    }

    private static void closeConnection() {
        try {
            connection.close(); //Cierre de la conexión
            System.out.println("Conexión con la BD cerrada");
        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
        }
    }


}


