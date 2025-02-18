package com.kristina.ecom.domain;

import java.util.List;

public class ComputerDecorator<K> implements Computer<K>{
  private Computer<K> computer;

  public ComputerDecorator(Computer<K> computer) {
    this.computer = computer;
  }

  @Override
  public String getDescription() {
    return this.computer.getDescription();
  }

  @Override
  public double getPrice() {
    return this.computer.getPrice();
  }
  
  @Override
  public String getOrderID() {
    return this.computer.getOrderID();
  }

  @Override
  public List<Product<K>> getComponents() {
    return this.computer.getComponents();
  }

  @Override
  public String toString() {
    return "ComputerDecorator [computer=" + computer + ", getDescription()=" + getDescription() + ", getPrice()="
        + getPrice() + ", getOrderID()=" + getOrderID() + ", getComponents()=" + getComponents() + "]";
  }

  public Computer<K> getComputer() {
    return computer;
  }
}
