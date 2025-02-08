package com.kristina.ecom.console.Mongo;

public class Main {
  public static void main(String[] args) {
    ProductDAOMongo productDao = new ProductDAOMongo();

    int result = productDao.read(1);
    System.out.println(result);
  }
}
