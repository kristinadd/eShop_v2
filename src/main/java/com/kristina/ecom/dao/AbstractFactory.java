package com.kristina.ecom.dao;

public interface AbstractFactory {
  public AbstractFactory create(DAOType dao);

  public static AbstractFactory get(DAOType dao) {
    
  }
}
