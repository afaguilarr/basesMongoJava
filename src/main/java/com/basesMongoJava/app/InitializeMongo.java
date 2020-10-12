package com.basesMongoJava.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.HashMap;

public class InitializeMongo {

    /**
     * This should be executed just once
     */
    public static void initializeCollections(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        /*mongoClient.getDatabase("local").getCollection("sucursal").drop();
        mongoClient.getDatabase("local").getCollection("ciudad").drop();
        mongoClient.getDatabase("local").getCollection("departamento").drop();
        mongoClient.getDatabase("local").getCollection("pais").drop();
        mongoClient.getDatabase("local").getCollection("vendedor").drop();
        mongoClient.getDatabase("local").getCollection("gremio").drop();
        mongoClient.getDatabase("local").getCollection("producto").drop();
        mongoClient.getDatabase("local").getCollection("marca").drop();*/
        mongoClient.getDatabase("local").createCollection("sucursal");
        mongoClient.getDatabase("local").createCollection("ciudad");
        mongoClient.getDatabase("local").createCollection("departamento");
        mongoClient.getDatabase("local").createCollection("pais");
        mongoClient.getDatabase("local").createCollection("vendedor");
        mongoClient.getDatabase("local").createCollection("gremio");
        mongoClient.getDatabase("local").createCollection("producto");
        mongoClient.getDatabase("local").createCollection("marca");
        mongoClient.close();
    }

    /**
     * This should be executed just once
     */
    public static void initializeCollectionsData(){
        String collection = "pais";
        String clavePrimaria = "nombre";

        MongoDatabase.deleteAll(collection);

        HashMap <String, Object> documento = new HashMap<String, Object>(){{
            put("nombre", "Colombia");
            put("ventas", 2000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "United States");
            put("ventas", 4000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Ecuador");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "departamento";
        clavePrimaria = "codigo";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Antioquia");
            put("codigo", "A");
            put("ventas", 2000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "California");
            put("codigo", "CA");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Colorado");
            put("codigo", "CO");
            put("ventas", 9000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "ciudad";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Medellin");
            put("codigo", "M");
            put("ventas", 9000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Boulder");
            put("codigo", "B");
            put("ventas", 3000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "No idea");
            put("codigo", "NI");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "sucursal";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Sucursal UNO");
            put("codigo", "S1");
            put("ventas", 2000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Boulder place");
            put("codigo", "S2");
            put("ventas", 9000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Boulder second place");
            put("codigo", "S3");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "gremio";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Los Mejores (y)");
            put("codigo", "LM");
            put("ventas", 11000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Los Peores :(");
            put("codigo", "LP");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Apenas Empezando");
            put("codigo", "AE");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "vendedor";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Pedro");
            put("codigo", "V1");
            put("ventas", 2000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Pablo");
            put("codigo", "V2");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Pinky");
            put("codigo", "V3");
            put("ventas", 9000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "marca";
        clavePrimaria = "nombre";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Adidas");
            put("descripcion", "Ropita en general :v");
            put("ventas", 2000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Niki");
            put("descripcion", "Copias de Nike :)");
            put("ventas", 9000);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Marca vacia");
            put("descripcion", "No tenemos producto");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        collection = "producto";
        clavePrimaria = "codbarras";

        documento = new HashMap<String, Object>(){{
            put("nombre", "Tenis deportivos");
            put("codbarras", "PKi9");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);

        documento = new HashMap<String, Object>(){{
            put("nombre", "Producto 1");
            put("codbarras", "CodBarras1");
            put("ventas", 0);
        }};
        MongoDatabase.upsert(collection, clavePrimaria, documento);
    }

    public static void main( String[] args )
    {
        initializeCollectionsData();
    }
}
