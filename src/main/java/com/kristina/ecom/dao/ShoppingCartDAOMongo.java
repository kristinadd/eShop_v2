package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;
import java.sql.ResultSet;
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
import com.kristina.ecom.domain.ComputerBase;
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

  @Override // this creates a shoppig cart but returns computer
  public Computer<String> create(Computer<String> computer) throws DAOException {
    try {
      Document document = toShoppingDocument(computer);
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

  @Override  // read shopping cart id 
  public Computer<String> read(String id) throws DAOException {
    Document shoppingDocument = collection.find(eq("_id", new ObjectId(id))).first();

    if (shoppingDocument != null) {
      Computer<String> computer = toComputer(shoppingDocument);
      return computer;
    } else {
      System.out.println("❌ Coudn't find the shopping cart with is: " + id);
    }

    return null;
  }

  @Override
  public int update(Computer<String> order) throws DAOException {
    return 1;
  }

  @Override // delete the entire shopping cart
  public int delete(String id) throws DAOException {
    Document shopDocument = collection.find(eq("_id", new ObjectId(id))).first();

    try {
      DeleteResult result = collection.deleteOne(shopDocument);
      System.out.println("❌ Deleted: " + result.getDeletedCount());
      return (int) result.getDeletedCount();
    } catch (MatchException ex) {
      throw new DAOException("Coudn't delete the shopping cart", ex);
    }
  }

  // from object to document
    private Document toShoppingDocument(Computer<String> computer) throws DAOException {
    Document document = new Document();

    document.append("_id", new ObjectId());
    document.append("user_id", "");
    document.append("updated_at", new Date());
    document.append("status", "ffgg");
    document.append("computers", toComputerDocument(computer));  // one computer

    return document;
  }

  private Document toComputerDocument(Computer<String> computer) {
    Document computerDoc = new Document();
    computerDoc.append("_id", computer.getOrderID());
    computerDoc.append("description", computer.getDescription());
    computerDoc.append("price", computer.getPrice());
    computerDoc.append("products", toProductDocuments(computer.getComponents())); // many products

    return computerDoc;
  }

  private List<Document> toProductDocuments(List<Product<String>> products) {
    ProductDAOMongo productDao = new ProductDAOMongo(); // using productDAO ??
    List<Document>  productDocuments = new ArrayList<>();

    for (Product<String> product : products) {
      Document productDoc = productDao.toDocument(product);
      
      productDocuments.add(productDoc);
    }
    return productDocuments;
  }

  // from document to object
  private Computer<String> toComputer(Document shoppingDocument) {
    Document computers = shoppingDocument.get("computers", Document.class); // get the nested object first

    List<Document> productDocuments = computers.getList("products", Document.class);
    List<Product<String>> products = toProducts(productDocuments);

    Computer<String> computer = new ComputerBase<>(
      computers.getString("_id"),
      products
    );

    return computer;
  }

  private List<Product<String>> toProducts(List<Document> productDocuments) {
    List<Product<String>> products = new ArrayList<>();
    ProductDAOMongo productDao = new ProductDAOMongo();

    for (Document productDoc : productDocuments) {
      Product<String> product = productDao.toProduct(productDoc);
      products.add(product);
    }
    return products;
  }
}
