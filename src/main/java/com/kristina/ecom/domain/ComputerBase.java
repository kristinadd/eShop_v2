package com.kristina.ecom.domain;

import java.util.Random;
import java.util.stream.Collectors;

import com.kristina.ecom.service.ProductService;

import java.util.List;
import java.util.ArrayList;

public class ComputerBase implements Computer {
  private static final int SIZE = 100;
  private static List<Integer> ids = new Random().ints(1, SIZE+1).distinct().limit(SIZE).boxed().collect((Collectors.toList()));

  private String orderID;
  private String description;
  private double price;
  private List<Product<Integer>> components;

  public ComputerBase() {
      this(getID(), new ArrayList<Product<Integer>>());
  }

  public ComputerBase(String orderID, List<Product<Integer>> components) {
    Product<Integer> computer = new ProductService().getComputer();
    this.orderID = orderID;
    this.description = computer.getName();
    this.price = computer.getPrice();
    this.components = components;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public double getPrice() {
    return this.price;
  }

  @Override
  public String getOrderID() {
    return this.orderID;
  }

  @Override
  public List<Product<Integer>> getComponents() {
    return components;
  }

  @Override
  public String toString() {
    return "ComputerBase [orderID=" + orderID + ", description=" + description + ", price=" + price + ", components="
        + components + "]";
  }

  private static String getID() {
    return Integer.toString(ids.remove(0));
  }
}
