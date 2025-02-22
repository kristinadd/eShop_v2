package com.kristina.ecom.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

import com.kristina.ecom.dao.DAO;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.OrderDAOMySql;
import com.kristina.ecom.dao.ProductDAOMySql;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;

public class OrderService {
  private DAO<String, Order<Integer>> dao; // interface
  private DAO<Integer, Product<Integer>> daoP;

  public OrderService() {
    dao = new OrderDAOMySql();
    daoP = new ProductDAOMySql();
  }

  public int create(Order<Integer> order) {
    try {
      dao.create(order);
      Product<Integer> stock;
      for (Product<Integer> p : order.getProducts()) {
        stock = daoP.read(p.getId());
        stock.setQuantity(stock.getQuantity() - p.getQuantity());
        daoP.update(stock);
      }
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return 1;
  }

  public List<Order<Integer>> getAll() {
    List<Order<Integer>> orders = new ArrayList<>();
    try {
      orders = dao.readAll();
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return orders;
  }

  public Order<Integer> get(String id) {
    Order<Integer> order = null;
    try {
      order = dao.read(id);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return order;
  }

  public int delete(String id) {
    int rows = 0;
    try {
      rows = dao.delete(id);
    } catch (DAOException ex) {
      ex.printStackTrace();
    }
    return rows;
  }

  public int cancel(String id) {
    int rows = 0;
    try {
      ProductService pService  = new ProductService();
      dao.read(id).getProducts().forEach(p -> {
        Product<Integer> product = pService.get(p.getId());
        product.setQuantity(product.getQuantity() + p.getQuantity());
        pService.update(product);
      });

      rows = dao.delete(id);
    } catch ( DAOException ex) {
      System.out.println("Error cancelling the order");
    }
    return rows;
  }

  public boolean update(Order<Integer> order) {
    try {
        Order<Integer> oldOrder = dao.read(order.getId());
        Product<Integer>  productFromStock;
        int difference;
        List<Product<Integer>> oldProducts = oldOrder.getProducts();
        List<Product<Integer>> newOrderProducts = order.getProducts();
        List<Product<Integer>> commonProducts = newOrderProducts.stream().filter(p -> oldProducts.contains(p)).collect(Collectors.toList());
        List<Product<Integer>> onlyOldOrder = oldProducts.stream().filter(p -> !newOrderProducts.contains(p)).collect(Collectors.toList());
        List<Product<Integer>> onlyNewOrder = newOrderProducts.stream().filter(p -> !oldProducts.contains(p)).collect(Collectors.toList());
        // update existing product or add new product 
        commonProducts.addAll(onlyNewOrder);
        for (Product<Integer> product : commonProducts) {
          difference = product.getQuantity() - getProductQuantityById(oldProducts, product.getId());
          productFromStock = daoP.read(product.getId());
          productFromStock.setQuantity(productFromStock.getQuantity() - difference);
          daoP.update(productFromStock);
        }
        // delete product from order, increase the stock
        for (Product<Integer> product : onlyOldOrder) {
          productFromStock = daoP.read(product.getId());
          productFromStock.setQuantity(productFromStock.getQuantity() + product.getQuantity());
          daoP.update(productFromStock);
          }

      dao.update(order);
      return true;
    } catch (DAOException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  // get the product quantity by its id in the oder 
  private int getProductQuantityById(List<Product<Integer>> products, Integer id){
    for (Product<Integer> p : products) 
      if (p.getId() == id)
        return p.getQuantity();

    return 0; 
  } 
}
