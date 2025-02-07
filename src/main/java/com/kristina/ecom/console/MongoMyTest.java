package com.kristina.ecom.console;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import org.bson.Document; // This class is part of the MongoDB BSON (Binary JSON) library and is used to represent MongoDB documents in Java.

import com.mongodb.BasicDBObject; // It’s used to create a query object.
import com.mongodb.client.MongoClient; // MongoDB JAVA Driver --> The main interface for connecting to MongoDB.
import com.mongodb.client.MongoClients; // MongoDB JAVA Driver --> A factory class to create MongoClient instances.
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase; // Represents MongoDB database

public class MongoMyTest {
  public static void main(String[] args) {
    // DATABASE CONNECTION
    MongoClient mongo = MongoClients.create("mongodb://127.0.1:27017"); // This creates a connection to a MongoDB instance running on 127.0.1:27017.
    // Establishes a connection to MongoDB running on 127.0.1:27017
    // This connection is open but not closed; it should be closed when done
    MongoDatabase database  = mongo.getDatabase("ecom");
    // This retrieves the database named "ecom"
    // If the "ecom" database does not exist, MongoDB does not create it 
    // immediately; it will be created only when data is added.
    System.out.println(database.getName());
    database.listCollectionNames().forEach(System.out::println);    // database.listCollectionNames().forEach(collection -> System.out.println(collection));

    // DATABASE STATISTICS
    Document database_statistics = database.runCommand(new Document("dbstats", 1));     // runs a MongoDB command and returns a Document object.
    for(Map.Entry<String, Object> entry : database_statistics.entrySet()) {
      System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
    }

    // GET COLLECTION (TABLE)
    MongoCollection<Document> collection = database.getCollection("products");
    MongoCursor<Document> cursor = collection.find().iterator();
    while (cursor.hasNext()) {
      Document document = cursor.next();
      System.out.println(document);

      // PRODUCT VALUES
      System.out.println("--------------PRODUCT VALUES--------------");
      // Convert Collection to ArrayList for easy index access
      ArrayList<Object> product_values = new ArrayList<>(document.values()); // values returns a Collection<Object>
      System.out.println(product_values);
      System.out.printf("Product values: %s, %s\n", product_values.get(0), product_values.get(1));
    }

      // CONSTRUCT A QUERY using BasicDBObject
      System.out.println("-----------------------------------------------------------------------");
      BasicDBObject query = new BasicDBObject("type", new BasicDBObject("$eq", "Component"))
                              .append("price", new BasicDBObject("$eq", 2.99));

      collection.find(query).forEach((Consumer<Document>) document -> {System.out.println(document.toJson());});



  }
}
