package com.kristina.ecom.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;
public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO
    OrderDAOMongo orderDao = new OrderDAOMongo();
    ProductDAOMongo productDao = new ProductDAOMongo();


    try {
      int result7 = productDao.delete("67adc74748bdac22c0bfba99");
      System.out.println("🍋 " + result7);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }


    Product<String> product3 = new Product<String>("", "Component", "Monitor", 30.50, 10, "monitor.img");
    try {
      Product<String> product = productDao.create(product3);
      System.out.println("✅ " + product);
    } catch (DAOException ex) {
      ex.printStackTrace();
      // System.out.println(ex.getCause());
    }
                                                                  // READ A PRODUCT 
    try {
      Product<String> product = productDao.read("67ac63ac2a325047a6d12097");
      System.out.println("🦁 " + product);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
                                                             // ORDER TESTS
    // OrderDAOMongo orderDao = new OrderDAOMongo();

                                                // CREATE AN ORDER
    List<Product<Integer>> products = new ArrayList<>();

    Order order = new Order(
      "Order with generated id",
      70.99f,
      LocalDateTime.now(),
      products
    );

    try {
      Order order2 = orderDao.create(order);
      System.out.println("🆕 " + order2);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
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
      Order order4 = orderDao.read("67acbba2c6e95a55d2762bb3");
      System.out.println("🍊 " + order4);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
                                                      // UPDATE AN ORDER 
      try {
        Order order5 = orderDao.read("67ae0630e96b206cc9a9e695");
        order5.setDescription("UPDATED description");
        int result = orderDao.update(order5);
        System.out.println("👻 Made " + result + " updates.");
      } catch (DAOException ex) {
        ex.printStackTrace();
      }
                                                        // DELETE ORDER
      try {
        int result = orderDao.delete("67adc829df98d6064d86a500");
        System.out.println("❌ Deleted " + result + " orders.");
      } catch (DAOException ex) {
        ex.printStackTrace();
      }
  }
}
