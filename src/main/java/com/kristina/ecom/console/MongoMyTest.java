package com.kristina.ecom.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bson.Document; // This class is part of the MongoDB BSON (Binary JSON) library and is used to represent MongoDB documents in Java.

import com.kristina.ecom.domain.Product;
import com.mongodb.BasicDBObject; // It’s used to create a query object.
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient; // MongoDB JAVA Driver --> The main interface for connecting to MongoDB.
import com.mongodb.client.MongoClients; // MongoDB JAVA Driver --> A factory class to create MongoClient instances.
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase; // Represents MongoDB database
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

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

    System.out.println("----------------------------------------------DATABSE--STATISTICS------------------------------");
    Document database_statistics = database.runCommand(new Document("dbstats", 1));     // runs a MongoDB command and returns a Document object.
    for(Map.Entry<String, Object> entry : database_statistics.entrySet()) {
      System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
    }

    System.out.println("---------------------------------------RETRIEVE--COLLECTIONS--FROM--DATABSE----------------------");
    MongoCollection<Document> collection = database.getCollection("products");
    MongoCursor<Document> cursor = collection.find().iterator();
    while (cursor.hasNext()) {
      Document document = cursor.next();
      System.out.println(document);

      System.out.println("-----------------------------------------------RETRIEVE--ONLY--VALUES--FROM--DOCUMENT-------------------------------");
      ArrayList<Object> product_values = new ArrayList<>(document.values()); // values() method returns Collection<Object>
      System.out.println(product_values);
      System.out.printf("Product values: %s, %s\n", product_values.get(0), product_values.get(1));
    }

      System.out.println("-------------------------------CONSTRUCT--QUERY--USING--BASIC-DB-OBJECT----------------------------------------");
      BasicDBObject query = new BasicDBObject("type", new BasicDBObject("$eq", "Component"))
                              .append("price", new BasicDBObject("$eq", 2.99));

      collection.find(query).forEach((Consumer<Document>) document -> {
        System.out.println(document.toJson());
      });


    System.out.println("🟡---------------------------------CRUD--OPERATIONS--FOR--PRODUCT-------------------------------🟡");
    // set database and collection
    database = mongo.getDatabase("ecom");
    collection = database.getCollection("products");

    System.out.println("🟡------------------------------------CREATE--ONLY--1--PRODUCT----------------------------------🟡");
    // create Java object and convert it to document
    Product product = new Product(1, "Component", "Keyboard", 65.99, 15, "img");
    Document document = createProductDocument(product);

    // write to the database
    try {
      InsertOneResult result = collection.insertOne(document);
      System.out.println("Successfully inserted: " + result.getInsertedId() + " class: " + result.getClass());
    } catch (MongoException e) {
      // e.printStackTrace();
      System.out.println("🔴 ERROR 🔴");
    }

    System.out.println("🟡------------------------------------CREATE--(1+)--PRODUCT-------------------------------------🟡");
    List<Product> products = Arrays.asList(
      new Product(2, "Component", "Headset", 300.99, 10, "img"),
      new Product(3, "Component", "Mouse", 35.99, 5, "img"),
      new Product(4, "Component", "Iphone", 1065.99, 3, "img"),
      new Product(5, "Component", "Monitor", 1500.00, 5, "img"),
      new Product(6, "Component", "MacBook", 2500.00, 7, "img")
    );
    List<Document> documentList = new ArrayList<>();

    for (Product p : products) {
      documentList.add(createProductDocument(p));
    }

    try {
      InsertManyResult result = collection.insertMany(documentList);
      System.out.println("Inserted successfully: " + result.getInsertedIds());
    } catch (MongoException e) {
      // e.printStackTrace();
      System.out.println("🔴 ERROR 🔴");
    }

    
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static Document createProductDocument(Product product) {
    Document document = new Document();

    document.append("_id", product.getId());
    document.append("type", product.getType());
    document.append("name", product.getName());
    document.append("price", product.getPrice());
    document.append("image", product.getImg());

    return document;
  }
}
