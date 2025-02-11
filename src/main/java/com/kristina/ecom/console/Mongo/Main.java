package com.kristina.ecom.console.mongo;

import com.kristina.ecom.domain.Product;
import com.mongodb.MongoException;

public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO

    ProductDAOMongo productDao = new ProductDAOMongo();

    // Product product = productDao.get(17);
    // System.out.println(product);

    Product product2 = new Product(20, "Component", "Mouse mini", 30.50, 10, "mouse-image-mini.img");
    System.out.println(product2);
    try {
      int r = productDao.create(product2);
      System.out.println(r);
    } catch (MongoException e) {
      e.printStackTrace();
    }

    // Product product5 = productDao.get(0);
    // System.out.println(product5);

    // productDao.delete(20);

    // Product product4 = productDao.get(19);
    // System.out.println(product4);
    // product4.setPrice(878787.00);
    // productDao.update(product4);


    // ORDER DAO

    
  }
}
