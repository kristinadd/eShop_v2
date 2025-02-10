package com.kristina.ecom.console.Mongo;

import com.kristina.ecom.domain.Product;

public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO

    ProductDAOMongo productDao = new ProductDAOMongo();

    Product product = productDao.read(3);
    System.out.println(product);

    Product product2 = new Product(19, "Component", "Mouse", 30.50, 3, "mouse-image.img");
    System.out.println(product2);
    boolean result = productDao.create(product2);
    System.out.println(result);

    Product product5 = productDao.read(0);
    System.out.println(product5);

    productDao.delete(5);

    Product product4 = productDao.read(0);
    System.out.println(product4);
    product4.setPrice(878787.00);
    productDao.update(product4);


    // ORDER DAO

    
  }
}
