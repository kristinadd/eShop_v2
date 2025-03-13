package com.kristina.ecom.service;

import java.util.List;

import com.kristina.ecom.dao.DAO;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.DAOFactory;
import com.kristina.ecom.domain.ShoppingCart;

public class ShoppingCartService {
  private DAO<String, ShoppingCart> dao;

  public ShoppingCartService() {
    dao = DAOFactory.getInstance().create(DAO.Type.SHOPPING_CART_DAO);
  }

  public int create(ShoppingCart cart) {
    try {
      dao.create(cart);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return 1;
  }

  public List<ShoppingCart> readAll() {
    try {
      List<ShoppingCart> carts = dao.readAll();
      return carts;
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public ShoppingCart read(String id) {
    ShoppingCart cart;
    try {
      cart = dao.read(id);
      return cart;
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public ShoppingCart readOne(String id) {
    try {
      ShoppingCart  cart = dao.read(id);
      return cart;
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  // later
  public void update(String id) {
    try {
      ShoppingCart cart = dao.read(id);
      dao.update(cart);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
  }

  public int delete(String id) {
    try {
      int result = dao.delete(id);
      return result;
    } catch (DAOException ex) {
      System.out.println("❌ Delete failed");
      ex.printStackTrace();
    }
    return 0;
  }
}
