package com.basesMongoJava.app;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class MongoDatabase {

    public static MongoClient openConnection(){
        return MongoClients.create("mongodb://localhost:27017");
    }

    public static void upsert(String collection, String clavePrimaria,
                              HashMap<String, Object> documento){
        MongoClient mongoClient = openConnection();

        UpdateOptions options = new UpdateOptions();
        options.upsert(true);
        Bson documentToFind = Filters.eq(clavePrimaria, documento.get(clavePrimaria));
        // Bson documentToFind = Filters.eq("name", "Shubham");

        Document documentToUpsert = new Document();
        Document subDocument = new Document(documento);
        // subDocument.put("name", "Shubham3");
        // subDocument.put("company", "Baeldung3");
        documentToUpsert.put("$set", subDocument);

        mongoClient.getDatabase("local").getCollection(collection).updateOne(documentToFind,
                documentToUpsert, options);

        mongoClient.close();
    }

    public static ArrayList<Document> findAll(String collection){
        MongoClient mongoClient = openConnection();

        // Ordena descendente cualquier colección según sus ventas
        BasicDBObject sortByVentas = new BasicDBObject("ventas", -1);

        FindIterable iterable = mongoClient.getDatabase("local").getCollection(collection).find()
                .sort(sortByVentas)
                .limit(3);

        ArrayList<Document> docs = new ArrayList<>();
        iterable.into(docs);

        // for (Document doc : docs) {
        //     System.out.println(doc);}

        mongoClient.close();

        return docs;
    }

    public static void deleteAll(String collection){
        MongoClient mongoClient = openConnection();

        mongoClient.getDatabase("local").getCollection(collection).drop();
        mongoClient.getDatabase("local").createCollection(collection);

        mongoClient.close();
    }
}
