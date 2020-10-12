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
    }

    public static void main( String[] args )
    {
        // initializeCollectionsData();
    }
}
