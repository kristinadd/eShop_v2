package com.kristina.ecom.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;
public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO

    ProductDAOMongo productDao = new ProductDAOMongo();

    // Product product2 = new Product("21", "Component", "Monitor", 30.50, 10, "monitor.img");
    Product product3 = new Product("Component", "Monitor", 30.50, 10, "monitor.img");

    // try {
    //   Product product = productDao.create(product3);
    //   System.out.println(product);
    // } catch (DAOException ex ) {
    //   ex.printStackTrace();
    // }

    // try {
    //   int r = productDao.create(product2);
    //   System.out.println(r);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    //   // System.out.println(ex.getCause());
    // }
                                                             // ORDER TESTS
    OrderDAOMongo orderDao = new OrderDAOMongo();

                                                // CREATE AN ORDER
    // List<Product> products = new ArrayList<>();

    // Order order = new Order(
    //   "2",
    //   "my order",
    //   10.43f,
    //   LocalDateTime.now(),
    //   products
    // );

    // try {
    //   Order order2 = orderDao.create(order);
    //   System.out.println("🆕 " + order2);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
                                                  // READ ALL ORDERS
    try {
      List<Order> orders = orderDao.readAll();
      for (Order order3 : orders) {
        System.out.println("⭐️ " +  order3);
      }
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
                                                    // READ ONE ORDER
    try {
      Order order = orderDao.read("234");
      System.out.println("🟡 "+ order);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
                                                      // UPDATE AN ORDER
      try {
        Order order = orderDao.read("234");
        order.setDescription("UPDATED description");
        int result = orderDao.update(order);
        System.out.println("👻 Made " + result + " updates.");
      } catch (DAOException ex) {
        ex.printStackTrace();
      }
                                                        // DELETE ORDER
      try {
        int result = orderDao.delete("123");
        System.out.println("❌ Deleted " + result + " orders.");
      } catch (DAOException ex) {
        ex.printStackTrace();
      }
  }
}
