package com.kristina.ecom.console.Mongo;

import com.kristina.ecom.domain.Product;

public class Main {
  public static void main(String[] args) {
    ProductDAOMongo productDao = new ProductDAOMongo();

    Product product = productDao.read(3);
    System.out.println(product);

    Product product2 = new Product("Component", "RED Light", 230.50, 33, "red-light-image.img");

    System.out.println(product2);

    boolean result = productDao.create(product2);
    System.out.println(result);

    Product product3 = productDao.read(0);
    System.out.println(product3);
    boolean result3 = productDao.delete(product3.getId());
    System.out.println(result3);

    
  }
}
