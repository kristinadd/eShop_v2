package com.kristina.ecom.console.Mongo;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.kristina.ecom.domain.Product;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class ProductDAOMongo implements MongoDAO<Product>{
  private MongoDataSource dataSourceFactory;
  private MongoCollection<Document> collection;

  public ProductDAOMongo() {
    this.dataSourceFactory = MongoDataSource.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("products");
  }
  
  @Override
  public  Product get(int id) {
    Document document = collection.find(eq("_id", id)).first();

    if (document != null) {
      Product product = new Product(
        document.getInteger("_id"),
        document.getString("type"),
        document.getString("name"),
        document.getDouble("price"),
        document.getInteger("quantity"),
        document.getString("image")
      );
      return product;
    } else {
      System.out.println("Coudn't find product with id: " + id);
    }

    return null;
  }
  
  @Override
  public boolean create(Product product) {
    if (product != null) {
      Document document = toDocument(product);
      try {
        collection.insertOne(document);
      } catch (MongoException ex) {
        ex.printStackTrace();
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public Product update(Product product) {
    Bson query = eq("_id", product.getId());
    // Document document = toDocument(product);

    try {
      collection.replaceOne(query, toDocument(product));
    } catch (MongoException ex) {
      ex.printStackTrace();
    }
    return product;
  }

  @Override
  public boolean delete(int id) {
    collection.deleteOne(Filters.eq(id));
    return true;
  }

  private Document toDocument(Product product) {
    if (product != null) {
    Document document = new Document();
    document.append("_id", product.getId());
    document.append("type", product.getType());
    document.append("name", product.getName());
    document.append("price", product.getPrice());
    document.append("quantity", product.getQuantity());
    document.append("image", product.getImg());

    return document;
    } else {
      System.out.println("Can't convert to document. Product is either null or invalid.");
    }
    return null;
  }
}

// Since Mongo's documents can have different structure, 
// if I have to construct a Product but the Product in 
// Mongo doesn't have, let's say quantity, what then?
