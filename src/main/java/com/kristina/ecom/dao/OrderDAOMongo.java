package com.kristina.ecom.dao;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;


public class OrderDAOMongo  implements MongoDAO<String, Order> {
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection<Document> collection;

  public OrderDAOMongo() {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("order");
  }

  @Override
  public Order create(Order order) throws DAOException {
    if (order == null)
      return null;

    try {
      // create order
      return order;
    } catch (MongoException ex) {
      throw new DAOException("Order creation error", ex);
    }
  }

  @Override
  public List<Order> readAll() throws DAOException {
    List<Order> orders = new ArrayList<>();
    return orders;
  }

  @Override
  public Order read(String id) throws DAOException {
    Product product1 = new Product();
    Product product2 = new Product();
    List<Product> products = new ArrayList<>();
    products.add(product1);
    products.add(product2);

    Order order = new Order("1", "dummy order object", 123.12f, LocalDateTime.now(), products); // floating-point literals with a decimal point are considered double by default

    return order;
  }

  @Override
  public int update(Order order) throws DAOException {
    return 1;
  }

  @Override
  public int delete(String id) throws DAOException {
    return 1;
  }
}
