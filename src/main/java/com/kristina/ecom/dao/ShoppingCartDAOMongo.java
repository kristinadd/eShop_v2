package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.Product;

      /*
      {
      "_id": ObjectId(""),
      "user_id": string, --> prep for the User
      "modifies": datetime,
      "status": active, fulfilled, canceled,
      "computers": [
        "_id":  (this is for Computer, and the id is String. the string is manually created)
        "_id": string, "description": string, "price": double
        "products": [
          "_id": (this is now the product id) integer, --> 
          "quantity": integer
         ]
      ]
      }
      */ 

public class ShoppingCartDAOMongo  implements DAO<String, Computer<String>> {
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection<Document> collection;

  public ShoppingCartDAOMongo () {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("shoppingcart");
  }

   @Override
  public Computer<String> create(Computer<String> computer) throws DAOException {
    try {
      Document document = toDocument(computer);
      InsertOneResult result = collection.insertOne(document);
      System.out.println(result.getInsertedId());
    } catch (MongoException ex) {
      throw new DAOException("❌ Coudn't create the shopping cart", ex);
    }

    return null;
  }

  @Override
  public List<Computer<String>> readAll() throws DAOException {
    return null;
  }

  @Override
  public Computer<String> read(String id) throws DAOException {
    return null;
  }

  @Override
  public int update(Computer<String> order) throws DAOException {
    return 1;
  }

  @Override
  public int delete(String id) throws DAOException {
    return 0;
  }

  private Document toDocument(Computer<String> computer) throws DAOException {
    Document document = new Document();

    document.append("_id", new ObjectId());
    document.append("user_id", "");
    document.append("updated_at", new Date());
    document.append("status", "ffgg");

    Document computerDoc = new Document();
    computerDoc.append("_id", computer.getOrderID());
    computerDoc.append("description", computer.getDescription());
    computerDoc.append("price", computer.getPrice());

    List<Document> productDocs = new ArrayList<>();
    List<Product<String>> products = computer.getComponents();
    for (Product<String> product : products) {
      ProductDAOMongo productDao = new ProductDAOMongo();
      Document prodDoc = productDao.toDocument(product);
      productDocs.add(prodDoc);
    }
    computerDoc.append("products", productDocs);
    
    document.append("computers", computerDoc);

    return document;
  }
}
