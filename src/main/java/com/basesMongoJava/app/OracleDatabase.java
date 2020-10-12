package com.basesMongoJava.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    static void updateSucursales() {
        startConnection();

        try {
            System.out.println("Seleccionando Sucursales");
            String query = "SELECT SUCURSAL.codigo, SUCURSAL.nombre, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN SUCURSAL " +
                    "ON VENTA.codigo_sucursal = SUCURSAL.codigo " +
                    "GROUP BY SUCURSAL.codigo, SUCURSAL.nombre";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("sucursal");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codigo", resultado.getString("codigo"));
                    put("nombre", resultado.getString("nombre"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("sucursal", "codigo", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Sucursales");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateCiudades() {
        startConnection();

        try {
            System.out.println("Seleccionando Ciudades");
            String query = "SELECT SUCURSAL_CIUDAD.codigo_ciudad, SUCURSAL_CIUDAD.nombre_ciudad, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN ( " +
                    "SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD.codigo AS codigo_ciudad, CIUDAD.nombre AS nombre_ciudad " +
                    "FROM SUCURSAL " +
                    "RIGHT JOIN CIUDAD " +
                    "ON SUCURSAL.codigo_ciudad = CIUDAD.codigo " +
                    ") SUCURSAL_CIUDAD " +
                    "ON VENTA.codigo_sucursal = SUCURSAL_CIUDAD.codigo_sucursal " +
                    "GROUP BY SUCURSAL_CIUDAD.codigo_ciudad, SUCURSAL_CIUDAD.nombre_ciudad";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("ciudad");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codigo", resultado.getString("codigo_ciudad"));
                    put("nombre", resultado.getString("nombre_ciudad"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("ciudad", "codigo", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Ciudades");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateDepartamento() {
        startConnection();

        try {
            System.out.println("Seleccionando Departamentos");
            String query = "SELECT SUCURSAL_DPTO.codigo_dpto, SUCURSAL_DPTO.nombre_dpto, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN ( " +
                    "SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD_DPTO.codigo_dpto AS codigo_dpto, CIUDAD_DPTO.nombre_dpto AS nombre_dpto " +
                    "FROM SUCURSAL " +
                    "RIGHT JOIN ( " +
                    "SELECT CIUDAD.codigo AS codigo_ciudad, DPTO.codigo AS codigo_dpto, DPTO.nombre AS nombre_dpto " +
                    "FROM CIUDAD " +
                    "RIGHT JOIN DPTO " +
                    "ON CIUDAD.codigo_dpto = DPTO.codigo " +
                    ") CIUDAD_DPTO " +
                    "ON SUCURSAL.codigo_ciudad = CIUDAD_DPTO.codigo_ciudad " +
                    ") SUCURSAL_DPTO " +
                    "ON VENTA.codigo_sucursal = SUCURSAL_DPTO.codigo_sucursal " +
                    "GROUP BY SUCURSAL_DPTO.codigo_dpto, SUCURSAL_DPTO.nombre_dpto";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("departamento");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codigo", resultado.getString("codigo_dpto"));
                    put("nombre", resultado.getString("nombre_dpto"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("departamento", "codigo", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Departamentos");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updatePaises() {
        startConnection();

        try {
            System.out.println("Seleccionando Paises");
            String query = "SELECT SUCURSAL_PAIS.nombre_pais, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN ( " +
                    "SELECT SUCURSAL.codigo AS codigo_sucursal, CIUDAD_PAIS.nombre_pais AS nombre_pais " +
                    "FROM SUCURSAL " +
                    "RIGHT JOIN ( " +
                    "SELECT CIUDAD.codigo AS codigo_ciudad, DPTO_PAIS.nombre_pais AS nombre_pais " +
                    "FROM CIUDAD " +
                    "RIGHT JOIN ( " +
                    "SELECT DPTO.codigo AS codigo_dpto, PAIS.nombre AS nombre_pais " +
                    "FROM DPTO " +
                    "RIGHT JOIN PAIS " +
                    "ON DPTO.nombre_pais = PAIS.nombre " +
                    ") DPTO_PAIS " +
                    "ON CIUDAD.codigo_dpto = DPTO_PAIS.codigo_dpto " +
                    ") CIUDAD_PAIS " +
                    "ON SUCURSAL.codigo_ciudad = CIUDAD_PAIS.codigo_ciudad " +
                    ") SUCURSAL_PAIS " +
                    "ON VENTA.codigo_sucursal = SUCURSAL_PAIS.codigo_sucursal " +
                    "GROUP BY SUCURSAL_PAIS.nombre_pais";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("pais");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("nombre", resultado.getString("nombre_pais"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("pais", "nombre", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Paises");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateGremios() {
        startConnection();

        try {
            System.out.println("Seleccionando Gremios");
            String query = "SELECT VENDEDOR_GREMIO.codigo_gremio, VENDEDOR_GREMIO.nombre_gremio, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN ( " +
                    "SELECT VENDEDOR.codigo AS codigo_vendedor, GREMIO.codigo AS codigo_gremio, GREMIO.nombre AS nombre_gremio " +
                    "FROM VENDEDOR " +
                    "RIGHT JOIN GREMIO " +
                    "ON VENDEDOR.codigo_gremio = GREMIO.codigo " +
                    ") VENDEDOR_GREMIO " +
                    "ON VENTA.codigo_vendedor = VENDEDOR_GREMIO.codigo_vendedor " +
                    "GROUP BY VENDEDOR_GREMIO.codigo_gremio, VENDEDOR_GREMIO.nombre_gremio";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("gremio");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codigo", resultado.getString("codigo_gremio"));
                    put("nombre", resultado.getString("nombre_gremio"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("gremio", "codigo", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Gremios");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateProductos() {
        startConnection();

        try {
            System.out.println("Seleccionando Productos");
            String query = "SELECT PRODUCTO.codbarras, PRODUCTO.nombre, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN PRODUCTO " +
                    "ON VENTA.codbarras_producto = PRODUCTO.codbarras " +
                    "GROUP BY PRODUCTO.codbarras, PRODUCTO.nombre";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("producto");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codbarras", resultado.getString("codbarras"));
                    put("nombre", resultado.getString("nombre"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("producto", "codbarras", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Productos");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateVendedores() {
        startConnection();

        try {
            System.out.println("Seleccionando Vendedores");
            String query = "SELECT VENDEDOR.codigo, VENDEDOR.nombre, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN VENDEDOR " +
                    "ON VENTA.codigo_vendedor = VENDEDOR.codigo " +
                    "GROUP BY VENDEDOR.codigo, VENDEDOR.nombre";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("vendedor");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("codigo", resultado.getString("codigo"));
                    put("nombre", resultado.getString("nombre"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("vendedor", "codigo", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Vendedores");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static void updateMarcas() {
        startConnection();

        try {
            System.out.println("Seleccionando Marcas");
            String query = "SELECT PRODUCTO_MARCA.nombre_marca, PRODUCTO_MARCA.descripcion_marca, SUM(valor) AS valor_total " +
                    "FROM VENTA " +
                    "RIGHT JOIN ( " +
                    "SELECT PRODUCTO.codbarras AS codbarras_producto, MARCA.nombre AS nombre_marca, MARCA.descripcion AS descripcion_marca " +
                    "FROM PRODUCTO " +
                    "RIGHT JOIN MARCA " +
                    "ON PRODUCTO.nombre_marca = MARCA.nombre " +
                    ") PRODUCTO_MARCA " +
                    "ON VENTA.codbarras_producto = PRODUCTO_MARCA.codbarras_producto " +
                    "GROUP BY PRODUCTO_MARCA.nombre_marca, PRODUCTO_MARCA.descripcion_marca";

            resultado = sentencia.executeQuery(query);

            MongoDatabase.deleteAll("marca");
            while (resultado.next()) {
                HashMap<String, Object> documento = new HashMap<String, Object>(){{
                    put("nombre", resultado.getString("nombre_marca"));
                    put("descripcion", resultado.getString("descripcion_marca"));
                    put("ventas", resultado.getInt("valor_total"));
                }};
                MongoDatabase.upsert("marca", "nombre", documento);
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select Marcas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

}


