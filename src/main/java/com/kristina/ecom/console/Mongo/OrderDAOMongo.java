package com.kristina.ecom.console.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;


public class OrderDAOMongo implements MongoDAO<Order> {
  private MongoDataSource dataSourceFactory;
  private MongoCollection<Document> collection;

  public OrderDAOMongo() {
    this.dataSourceFactory = MongoDataSource.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("orders");
  }

  @Override
  public Order get(int id) {
    Document document = collection.find(eq("_id", id)).first();

    if (document != null) {
      Order order = new Order(
        document.getString("_id"),
        document.getString("description"),
        document.getInteger("price").floatValue(),
        document.get("date", LocalDateTime.class),
        document.getList("products", Product.class)
      );
      return order;
    } else {
      System.out.println("Coudn't find order with id: " + );
    }
    return null;
  }
}
