package com.kristina.ecom.domain;
public class Component<K> extends ComputerDecorator<K> {
  private String description;
  private double price;

  public Component(Computer<K> computer) {
    super(computer);
  }

  public Component(Computer<K> computer, Product<K> product) {
    super(computer);
    this.description = product.getName();
    this.price = product.getPrice();
    if (super.getComponents().contains(product)) {
      Product<K> p = super.getComponents().get(super.getComponents().indexOf(product));
       p.setQuantity(p.getQuantity() + product.getQuantity());
    } else
      super.getComponents().add(product);
  }

  @Override
  public String getDescription() {
    return super.getDescription() + " + " + this.description;
  }

  @Override
  public double getPrice() {
    return super.getPrice() + this.price;
  }

  @Override
  public String toString() {
    return String.format("order_id --> %s, description --> %s, price --> s%.2f", this.getOrderID(), this.getDescription(), this.getPrice());
  }
}
