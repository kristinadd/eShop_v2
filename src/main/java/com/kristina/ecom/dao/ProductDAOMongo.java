package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.kristina.ecom.domain.Product;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class ProductDAOMongo implements MongoDAO<String, Product<String>> {
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection<Document> collection;

  public ProductDAOMongo() {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("products");
  }

  @Override
  public Product<String> create(Product<String> product) throws DAOException {
    if (product == null)
      return null;
      
      try {
      Document document = toDocument(product);
      InsertOneResult result = collection.insertOne(document);
      product.setId(result.getInsertedId().toString());
      return product;
      } catch (MongoException ex) {
        throw new DAOException("Product creation error", ex);
      }
   }
  
  @Override
  public  Product<String> read(String id) throws DAOException {
    Document document = collection.find(eq("_id", new ObjectId(id))).first();

    if (document != null) {
      Product<String> product = toProduct(document);
      return product;
    } else {
      System.out.println("Coudn't find product with id: " + id);
    }

    return null;
  }

  @Override
  public List<Product<String>> readAll() throws DAOException {
    List<Product<String>> products = new ArrayList<>();
    FindIterable<Document> documents = collection.find();
    for (Document document : documents) {
      if (document != null) {
        Product<String> product = toProduct(document);
      products.add(product);
      }
    }
    return products;
  }

  @Override
  public int update(Product<String> product) throws DAOException {
    if (product == null)
      return 0;

    try {
    Bson query = eq("_id", product.getId());
    UpdateResult result = collection.replaceOne(query, toDocument(product));
    return (int) result.getModifiedCount();
    } catch (MongoException ex) {
      throw new DAOException("Product update error.", ex);
    }
  }

  @Override
  public int delete(String id) throws DAOException {
    try {
    DeleteResult result = collection.deleteOne(Filters.eq(id));
    return (int) result.getDeletedCount();
    } catch (MongoException ex) {
      throw new DAOException("Product delete error", ex);
    }
  }

  private Product<String> toProduct(Document document) {
    if (document == null)
      return null;

    Product<String> product = new Product<String>(
      document.getObjectId("_id").toString(),
      document.getString("type"),
      document.getString("name"),
      document.getDouble("price"),
      document.getInteger("quantity"),
      document.getString("image")
      );
    return product;

  }

  private Document toDocument(Product<String> product) {
    if (product != null) {
    Document document = new Document();
      if (!product.getId().isEmpty())
        document.append("_id", product.getId());  // maybe need to append as new ObjectId(product.getId()) 
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

