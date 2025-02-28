package com.kristina.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kristina.ecom.dao.DAO;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.DAOFactory;
import com.kristina.ecom.dao.DAOType;
import com.kristina.ecom.domain.Product;

public class ProductService {
  private DAO<String, Product<String>> dao;

  public ProductService() {
    dao = DAOFactory.getDAO(DAOType.PRODUCT_SQL);
  }

  public List<Product<String>> getAll() {
    List<Product<String>> products = new ArrayList<>();
    try {
      products = dao.readAll()
      .stream()
      .filter(product -> product.getType()
      .equals("Component"))
      .collect(Collectors.toList());


    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return products;
  }

  public Product<String> getComputer() {
    Product<String> product = null;
    try {
      product = dao.readAll().stream()
            .filter(p -> p.getType()
            .equals("Compute"))
            .findFirst().orElse(null);
    } catch (DAOException ex) {
        ex.printStackTrace();
    }

    return product;
  }

  public Product<String> get(String id) {
    Product<String> product = null;
    try {
      product = dao.read(String.valueOf(id));
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return product;
  }

  public int delete(String id) {
    int rows = 0;
    try {
      rows = dao.delete(String.valueOf(id));
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return rows;
  }

  public int create(Product<String> product) {
    try {
      dao.create(product);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return 1;
  }

  public int update (Product<String> product) {
    int rows = 0;
    try {
      rows = dao.update(product);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return rows;
  }
}
