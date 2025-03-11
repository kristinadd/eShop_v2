package com.kristina.ecom.dao;

public class SQLFactory implements AbstractFactory {
  private static SQLFactory instance= new SQLFactory();

  private SQLFactory() {}

  public SQLFactory getInstance() {
    return instance;
  }
  
  public AbstractFactory create(DAOType dao) {
    if (dao == null) {
      return null;
    }

    switch (dao) {
      case DAOType.ORDER_SQL:
        return new OrderDAOMySql();
      case DAOType.PRODUCT_SQL:
        return new ProductDAOMySql();
      default:
      return null;
    }
  }
}
