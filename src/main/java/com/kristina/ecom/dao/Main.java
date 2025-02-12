package com.kristina.ecom.dao;

import com.kristina.ecom.domain.Product;
public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO

    ProductDAOMongo productDao = new ProductDAOMongo();

    // Product product2 = new Product("21", "Component", "Monitor", 30.50, 10, "monitor.img");
    Product product3 = new Product("Component", "Monitor", 30.50, 10, "monitor.img");

    try {
      Product product = productDao.create(product3);
      System.out.println(product);
    } catch (DAOException ex ) {
      ex.printStackTrace();
    }

    // try {
    //   int r = productDao.create(product2);
    //   System.out.println(r);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    //   // System.out.println(ex.getCause());
    // }
  }
}
