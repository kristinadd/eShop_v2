package com.kristina.ecom.dao;

import java.util.List;

import com.kristina.ecom.domain.Product;
import com.mongodb.MongoException;

public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO

    ProductDAOMongo productDao = new ProductDAOMongo();

    Product product2 = new Product("21", "Component", "Monitor", 30.50, 10, "monitor.img");
    Product product3 = new Product("Component", "Monitor", 30.50, 10, "monitor.img");

    try {
      Product product = productDao.create(product3);
      System.out.println(product);
    } catch (DAOException ex ) {
      ex.printStackTrace();
    }

    // System.out.println(product2);

    // Product product3 = productDao.read(5);
    // System.out.println(product3);

    // product3.setPrice(400);
    // product3.setQuantity(10);

    // int result = productDao.update(product3);
    // System.out.println(result);

    // System.out.println(product3);


    // List<Product> products = productDao.readAll();
    // for (Product product : products) {
    //   System.out.println(product);
    // }



    // try {
    //   int r = productDao.create(product2);
    //   System.out.println(r);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    //   // System.out.println(ex.getCause());
    // }
  }
}
