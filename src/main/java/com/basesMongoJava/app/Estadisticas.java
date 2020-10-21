package com.basesMongoJava.app;

import org.bson.Document;

import java.util.ArrayList;

public class Estadisticas {

    public static String getPaises (ArrayList<Document> lista){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("país:\n\n");
        for (Document document : lista){
            stringBuilder.append("Nombre de país: ");
            stringBuilder.append(document.get("nombre"));
            stringBuilder.append("\n");
            stringBuilder.append("Ventas: ");
            stringBuilder.append(document.get("ventas"));
            stringBuilder.append("\n\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static String getOtraCosa (String queEs, ArrayList<Document> lista){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(queEs);
        stringBuilder.append(":\n\n");
        for (Document document : lista){
            stringBuilder.append("Nombre de ");
            stringBuilder.append(queEs);
            stringBuilder.append(": ");
            stringBuilder.append(document.get("nombre"));
            stringBuilder.append("\n");
            stringBuilder.append("Código: ");
            stringBuilder.append(document.get("codigo"));
            stringBuilder.append("\n");
            stringBuilder.append("Ventas: ");
            stringBuilder.append(document.get("ventas"));
            stringBuilder.append("\n\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static String getProductos (ArrayList<Document> lista){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("producto:\n\n");
        for (Document document : lista){
            stringBuilder.append("Nombre de producto: ");
            stringBuilder.append(document.get("nombre"));
            stringBuilder.append("\n");
            stringBuilder.append("Código de barras: ");
            stringBuilder.append(document.get("codbarras"));
            stringBuilder.append("\n");
            stringBuilder.append("Ventas: ");
            stringBuilder.append(document.get("ventas"));
            stringBuilder.append("\n\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static String getMarcas (ArrayList<Document> lista){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("marca:\n\n");
        for (Document document : lista){
            stringBuilder.append("Nombre de marca: ");
            stringBuilder.append(document.get("nombre"));
            stringBuilder.append("\n");
            stringBuilder.append("Descripción: ");
            stringBuilder.append(document.get("descripcion"));
            stringBuilder.append("\n");
            stringBuilder.append("Ventas: ");
            stringBuilder.append(document.get("ventas"));
            stringBuilder.append("\n\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
