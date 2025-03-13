package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.kristina.ecom.domain.ShoppingCart;
import com.kristina.ecom.domain.Status;
import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.ComputerBase;
import com.kristina.ecom.domain.Product;
public class ShoppingCartDAOMongo  implements DAO<String, ShoppingCart> {
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection<Document> collection;

  public ShoppingCartDAOMongo () {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("shoppingcart");
  }

  @Override
  public ShoppingCart create(ShoppingCart shoppingCart) throws DAOException {
    try {
      Document document = toShoppingDocument(shoppingCart);
      InsertOneResult result = collection.insertOne(document);
      System.out.println(result.getInsertedId());
    } catch (MongoException ex) {
      throw new DAOException("❌ Coudn't create the shopping cart", ex);
    }

    return null;
  }

  @Override 
  public List<ShoppingCart> readAll() throws DAOException {
    FindIterable<Document> cartDocuments = collection.find();
    List<ShoppingCart> carts = new ArrayList<>();
    for (Document document : cartDocuments) {
      ShoppingCart cart = toShoppingCart(document);
      carts.add(cart);
    }
    return carts;
  }

  @Override 
  public ShoppingCart read(String id) throws DAOException {
    Document shoppingDocument = collection.find(eq("_id", id)).first();
    ShoppingCart cart = toShoppingCart(shoppingDocument);

    return cart;
  }

  @Override
  public int update(ShoppingCart shoppingCart) throws DAOException {
    return 1;
  }

  @Override // delete the entire shopping cart
  public int delete(String id) throws DAOException {
    Document shopDocument = collection.find(eq("_id", new ObjectId(id))).first();
    if (shopDocument != null) {
      try {
        DeleteResult result = collection.deleteOne(shopDocument);
        System.out.println("❌ Deleted: " + result.getDeletedCount());
        return (int) result.getDeletedCount();
      } catch (MatchException ex) {
        throw new DAOException("Coudn't delete the shopping cart", ex);
      }
    }
    return 0;
  }

  // from object to document
    private Document toShoppingDocument(ShoppingCart shoppingCart) throws DAOException {
    Document document = new Document();

    document.append("_id", shoppingCart.getId());
    document.append("user_id", shoppingCart.getUserId());
    document.append("updated_at", shoppingCart.getUpdatedAt());
    document.append("status", shoppingCart.getStatus());

    List<Document> computerDocuments = new ArrayList<>();
    List<Computer> computers = shoppingCart.getComputers();
      for (Computer computer : computers) {
        Document computerDoc = toComputerDocument(computer);
        computerDocuments.add(computerDoc);
      }

    document.append("computers", computerDocuments);
    return document;
  }

  private Document toComputerDocument(Computer computer) {
    Document computerDoc = new Document();
    computerDoc.append("_id", computer.getOrderID());
    computerDoc.append("description", computer.getDescription());
    computerDoc.append("price", computer.getPrice());
    computerDoc.append("products", toProductDocuments(computer.getComponents())); // many products

    return computerDoc;
  }

  private List<Document> toProductDocuments(List<Product> products) {
    List<Document>  productDocuments = new ArrayList<>();

    for (Product product : products) {
      Document productDoc = UtilDAOMongo.toDocument(product);      // use UtilMongo instead of calling the productDAO directly
      
      productDocuments.add(productDoc);
    }
    return productDocuments;
  }

  // from document to object
  private Computer toComputer(Document document) {
    Document computers = document.get("computers", Document.class); // get the nested object first

    List<Document> productDocuments = computers.getList("products", Document.class);
    List<Product> products = toProducts(productDocuments);

    Computer computer = new ComputerBase(
      computers.getString("_id"),
      products
    );

    return computer;
  }

  private List<Product> toProducts(List<Document> productDocuments) {
    List<Product> products = new ArrayList<>();

    for (Document productDoc : productDocuments) {
      Product product = UtilDAOMongo.toProduct(productDoc);
      products.add(product);
    }
    return products;
  }

  private ShoppingCart toShoppingCart(Document document) {
    List<Computer> computers = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    List<Document> computerDocuments = new ArrayList<>();
    
    computerDocuments = document.getList("computers", Document.class);
    if (computerDocuments != null) {
      for (Document doc : computerDocuments) {
        System.out.println(doc);

        List<Document> productDocs = doc.getList("products", Document.class);
        for (Document proDoc : productDocs) {
          Product product = new Product(
            proDoc.getInteger("_id"),
            proDoc.getString("type"),
            proDoc.getString("name"),
            proDoc.getDouble("price"),
            proDoc.getInteger("quantity"),
            proDoc.getString("image")
          );
          products.add(product);
        }

        Computer computer = new ComputerBase(
          doc.getString("_id"),
          products
        );
        computers.add(computer);
      }
    } else {
      System.out.println("❌ computerDocuments is null");
    }

    ShoppingCart cart = new ShoppingCart(
    document.getString("_id"),
    document.getString("user_id"),
    document.getDate("updated_at"),
    Status.valueOf(document.getString("status")),
    computers
    );

    return cart;
  }
}
