package com.kristina.ecom.console.Mongo;

public class Main {
  public static void main(String[] args) {
    ProductDAOMongo productDao = new ProductDAOMongo();

    productDao.read(1);
  }
}
