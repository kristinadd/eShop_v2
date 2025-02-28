package com.kristina.ecom.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

import com.kristina.ecom.dao.DAO;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.DAOFactory;
import com.kristina.ecom.dao.DAOType;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;

public class OrderService {
  private DAO<String, Order<String>> dao; // interface
  private DAO<Integer, Product<String>> daoP;

  public OrderService() {
    dao = DAOFactory.getDAO(DAOType.ORDER_SQL);
    daoP = DAOFactory.getDAO(DAOType.PRODUCT_SQL);
  }

  public int create(Order<String> order) {
    try {
      dao.create(order);
      Product<String> stock;
      for (Product<String> p : order.getProducts()) {
        stock = daoP.read(p.getId());
        stock.setQuantity(stock.getQuantity() - p.getQuantity());
        daoP.update(stock);
      }
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return 1;
  }

  public List<Order<String>> getAll() {
    List<Order<String>> orders = new ArrayList<>();
    try {
      orders = dao.readAll();
    } catch (DAOException ex) {
      ex.printStackTrace();
    }

    return orders;
  }

  public Order<String> get(String id) {
    Order<String> order = null;
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
        Product<String> product = pService.get(p.getId());
        product.setQuantity(product.getQuantity() + p.getQuantity());
        pService.update(product);
      });

      rows = dao.delete(id);
    } catch ( DAOException ex) {
      System.out.println("Error cancelling the order");
    }
    return rows;
  }

  public boolean update(Order<String> order) {
    try {
        Order<String> oldOrder = dao.read(order.getId());
        Product<String>  productFromStock;
        int difference;
        List<Product<String>> oldProducts = oldOrder.getProducts();
        List<Product<String>> newOrderProducts = order.getProducts();
        List<Product<String>> commonProducts = newOrderProducts.stream().filter(p -> oldProducts.contains(p)).collect(Collectors.toList());
        List<Product<String>> onlyOldOrder = oldProducts.stream().filter(p -> !newOrderProducts.contains(p)).collect(Collectors.toList());
        List<Product<String>> onlyNewOrder = newOrderProducts.stream().filter(p -> !oldProducts.contains(p)).collect(Collectors.toList());
        // update existing product or add new product 
        commonProducts.addAll(onlyNewOrder);
        for (Product<String> product : commonProducts) {
          difference = product.getQuantity() - getProductQuantityById(oldProducts, product.getId());
          productFromStock = daoP.read(product.getId());
          productFromStock.setQuantity(productFromStock.getQuantity() - difference);
          daoP.update(productFromStock);
        }
        // delete product from order, increase the stock
        for (Product<String> product : onlyOldOrder) {
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
  private int getProductQuantityById(List<Product<String>> products, String id){
    for (Product<String> p : products) 
      if (p.getId().equals(id))
        return p.getQuantity();

    return 0; 
  } 
}
