package com.kristina.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kristina.ecom.dao.DAO;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.DAOFactory;
import com.kristina.ecom.dao.DAOType;
import com.kristina.ecom.dao.ProductDAOMySql;
import com.kristina.ecom.domain.Product;

public class ProductService {
  private DAO<Integer, Product<Integer>> dao;

  public ProductService() {
    dao = DAOFactory.getDAO(DAOType.PRODUCT_SQL);
  }

  public List<Product<Integer>> getAll() {
    List<Product<Integer>> products = new ArrayList<>();
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

  public Product<Integer> getComputer() {
    Product<Integer> product = null;
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

  public Product<Integer> get(Integer id) {
    Product<Integer> product = null;
    try {
      product = dao.read(id);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return product;
  }

  public int delete(Integer id) {
    int rows = 0;
    try {
      rows = dao.delete(id);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return rows;
  }

  public int create(Product<Integer> product) {
    try {
      dao.create(product);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return 1;
  }

  public int update (Product<Integer> product) {
    int rows = 0;
    try {
      rows = dao.update(product);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return rows;
  }
}
