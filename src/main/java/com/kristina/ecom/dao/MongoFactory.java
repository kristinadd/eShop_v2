package com.kristina.ecom.dao;

public class MongoFactory implements AbstractFactory {
  private static MongoFactory instance = new MongoFactory();

  private MongoFactory() {}

  public AbstractFactory create(DAOType dao) {
    if (dao == null) {
      return null;
    }

    if (dao ==  DAOType.SHOPPING_CART_MONGO) {
      return new ShoppingCartDAOMongo;
    } else {
      return null;
    }
  }
}
