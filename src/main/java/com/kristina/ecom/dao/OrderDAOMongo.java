package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;

public class OrderDAOMongo  implements MongoDAO<String, Order<String>> {
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection<Document> collection;

  public OrderDAOMongo() {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("orders");
  }

  @Override
  public Order<String> create(Order<String> order) throws DAOException {
    if (order == null)
      return null;

    try {
      Document document = toDocument(order);
      InsertOneResult result = collection.insertOne(document);
      order.setId(result.getInsertedId().toString());
      return order;
    } catch (MongoException ex) {
      throw new DAOException("Order creation error", ex);
    }
  }

  @Override
  public List<Order<String>> readAll() throws DAOException {
    FindIterable<Document> documents = collection.find();
    List<Order<String>> orders = new ArrayList<>();

    for (Document document : documents) {
      Order<String> order = toOrder(document);
      orders.add(order);
    }

    return orders;
  }

  @Override
  public Order<String> read(String id) throws DAOException {
    if (id == null) {
      return null;
    }
    Bson query =  eq("_id", new ObjectId(id));
    Document document = collection.find(query).first();

    if (document != null) {
      Order<String> order = toOrder(document);

      return order;
    }

    return null;
  }

  @Override
  public int update(Order<String> order) throws DAOException {
    if (order == null)
      return 0;

    Document document = toDocument(order);
    Bson query = eq("_id", new ObjectId(order.getId()));
    UpdateResult result = collection.replaceOne(query, document);

    return (int) result.getModifiedCount();
  }

  @Override
  public int delete(String id) throws DAOException {
    Bson query = Filters.eq("_id", new ObjectId(id));
    DeleteResult result = collection.deleteOne(query);

    return (int) result.getDeletedCount();
  }

  private Order<String> toOrder(Document document) {
    if (document == null)
      return null;

    Order<String> order = new Order<String>(
      document.getObjectId("_id").toString(),
      document.getString("description"),
      document.getDouble("total").floatValue(),
      document.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
      new ArrayList<Product<String>>()
      // document.getList("products", Product.class)
      // need to loop
    );

    return order;
  }

  private Document toDocument(Order<String> order) {
    if (order == null)
      return null;
    
    Document document = new Document();
    if (!order.getId().isEmpty())
    document.append("_id", new ObjectId(order.getId())); 
    document.append("description", order.getDescription());
    document.append("total", order.getTotal());
    document.append("date", order.getDate());
    document.append("products", order.getProducts());

    return document;
  }
}
