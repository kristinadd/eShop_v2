package com.kristina.ecom.domain;

import java.time.LocalDateTime;
import java.util.List;
public class Order<K> {
  private String id;
  private String description;
  private float total;
  private LocalDateTime date;
  private List<Product<K>> products;

  public Order(Computer<K> computer) {
      this(
      computer.getOrderID(), 
      computer.getDescription(), 
      (float) computer.getPrice(), 
      LocalDateTime.now(),
      computer.getComponents()
      );
  }
  
  public Order(String id, String description, float total, LocalDateTime date, List<Product<K>> products) {
    this.id = id;
    this.description = description;
    this.total = total;
    this.date = date;
    this.products= products;
  }

  public Order(String description, float total, LocalDateTime date, List<Product<K>> products) {
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
    ComputerBase<K> base = new ComputerBase<>();
    description = base.getDescription();
    total = (float)base.getPrice();
    
    for (Product<K> product : products) {
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

  public List<Product<K>> getProducts() {
    return products;
  }

  public void setProducts(List<Product<K>> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return String.format("OrderID@%s: %s $%.2f", this.id, this.description, this.total);
  }
}
