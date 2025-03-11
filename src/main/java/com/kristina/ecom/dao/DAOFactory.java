package com.kristina.ecom.dao;

public class DAOFactory {
  
  public static DAO getDAO(DAOType daoType) {
    if (daoType == null) {
      return null;
    }

    switch (daoType) {
      case DAOType.ORDER_SQL:
        return new OrderDAOMySql();
      case DAOType.PRODUCT_SQL:
        return new ProductDAOMySql();
      case DAOType.SHOPPING_CART_MONGO:
        return new ShoppingCartDAOMongo();
      default:
        throw new IllegalArgumentException("Unknown dao type: " + daoType);
    }
  }
}
