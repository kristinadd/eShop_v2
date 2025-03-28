package com.kristina.ecom.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
public class Order {
  private static final int SIZE = 100;
  private static List<Integer> ids = new Random().ints(1, SIZE+1).distinct().limit(SIZE).boxed().collect((Collectors.toList()));

  private String id;
  private String description;
  private float total;
  private LocalDateTime date;
  private List<Product> products;

  public Order(Computer computer) {
      this(
      getID(),
      computer.getDescription(), 
      (float) computer.getPrice(), 
      LocalDateTime.now(),
      computer.getComponents()
      );
  }
  
  public Order(String id, String description, float total, LocalDateTime date, List<Product> products) {
    this.id = id;
    this.description = description;
    this.total = total;
    this.date = date;
    this.products= products;
  }

  public Order(String description, float total, LocalDateTime date, List<Product> products) {
    this.id = "";
    this.description = description;
    this.total = total;
    this.date = date;
    this.products = products;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void update() {
    ComputerBase base = new ComputerBase();
    description = base.getDescription();
    total = (float)base.getPrice();
    
    for (Product product : products) {
      description += (" + " + product.getName()).repeat(product.getQuantity());
      total += product.getPrice() * product.getQuantity();
    }
    this.setDate(LocalDateTime.now());
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return String.format("OrderID@%s: %s $%.2f", this.id, this.description, this.total);
  }

  private static String getID() {
    return Integer.toString(ids.remove(0));
  }
}
