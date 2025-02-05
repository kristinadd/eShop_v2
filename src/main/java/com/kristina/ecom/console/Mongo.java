package com.kristina.ecom.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
// import static com.mongodb.client.model.Filters.gte;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.kristina.ecom.domain.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor; // iterate over the results
import com.mongodb.client.model.Projections; // instructions to see what i want to get back
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.InsertManyResult;

// Cursor is a pointer, leackage of resources, so i need to close it


public class Mongo {
  public static void main(String[] args) {
    MongoClient mongo = MongoClients.create("mongodb://127.0.1:27017");

    MongoDatabase database = mongo.getDatabase("test");
    System.out.println(database.getName());
    database.listCollectionNames().forEach(System.out::println);

    // databse stats
    var stats = database.runCommand(new Document("dbstats", 1));
    for (Map.Entry<String, Object> set : stats.entrySet()) {
      System.out.printf("%s: %s\n", set.getKey(), set.getValue());
    }

    // get a collection
    MongoCollection<Document> collection = database.getCollection("users");
    MongoCursor<Document> cursor = collection.find().iterator();
    while (cursor.hasNext()) {
      Document doc = cursor.next();
      System.out.println(doc);

      var users = new ArrayList<>(doc.values());
      System.out.printf("%s, %s\n", users.get(0), users.get(1));
    }

    // query MongoDB using BasicDBObject
    System.out.println("-----------------------------------------------------------------------");
    var query = new BasicDBObject("birthday", new BasicDBObject("$eq", "11/11/2003"));

    collection.find(query).forEach((Consumer<Document>) doc -> {
      System.out.println(doc.toJson());
    });

    // create a product
    database = mongo.getDatabase("ecom"); // create a new db ecom 
    System.out.println("-----------------------------------------------------------------------");
    Product product = new Product(4, "Component", "product4", 2.99, 7, "img");

    // everything is document, so i need to create a document
    Document document = createDoc(product);
    collection = database.getCollection("products");

    // CRUD create One
    System.out.println("---------------------------------CREATE--ONE--------------------------------------");
    try {
      InsertOneResult result = collection.insertOne(document);
      System.out.println("Successfully inserted: " + result.getInsertedId());
    } catch (MongoException e) {
      System.out.println("Failed to insert");
    }

    // CRUD create Many
    System.out.println("---------------------------------CREATE--MANY--------------------------------------");
    List<Product> products = Arrays.asList(
      new Product(1, "Component", "product1", 6.99, 7, "img"),
      new Product(2, "Component", "product2", 2.99, 7, "img"),
      new Product(3, "Component", "product3", 22.99, 7, "img")
    );
    // now need a list of documents
    // take the products and add them to the documents
    List<Document> productDocuments = new ArrayList<>();
    for (Product p : products) {
      productDocuments.add(createDoc(p));
    }

    try {
      InsertManyResult result = collection.insertMany(productDocuments);
      System.out.println("Inserted successfully: " + result.getInsertedIds());
    } catch (MongoException e ) {
      System.out.println("Failed to insert");
    }



    // CRUD read One
    System.out.println("-------------------------------READ--ONE--------------------------------------");
    Bson fields = Projections.fields(
      Projections.include("type", "name", "price"),
      Projections.excludeId()
      );

    // Document doc2 = collection.find(eq("name", "product1")).projection(fields).first();
    Document doc2 = collection.find(eq("type", "Component"))
      .projection(fields)
      .sort(Sorts.descending("price"))
      .first();
    if (doc2 != null) {
      System.out.println(doc2.toJson());
    }

    // CRUD read many
    System.out.println("---------------------------------MANY--------------------------------------");
    MongoCursor<Document> cursor2 = collection.find(lt("price", 10.0))
      .projection(fields)
      .sort(Sorts.ascending("price"))
      .iterator();

    try {
      while (cursor2.hasNext()) {
        System.out.println(cursor2.next().toJson());
      }
    } finally {
      cursor.close(); // close the connection to the database, connection pool
    }
  }

// convert object to document 
  private static Document createDoc(Product product) {
    Document document = new Document();

    document.append("_id", product.getId());
    document.append("type", product.getType());
    document.append("name", product.getName());
    document.append("price", product.getPrice());
    document.append("quantity", product.getQuantity());
    document.append("image", product.getImg());

    return document;

  }
}


// next time insert nested 

