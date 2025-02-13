package com.kristina.ecom.domain;

public class Product<K> implements Cloneable {
  private K id;
  private String type;
  private String name;
  private double price;
  private String img;
  private int quantity;

  public Product() {
    // default constructor
  }

  public Product(String type, String name, double price, String img) {
    this(null, type, name, price, 0, img);
  }

  public Product(String type, String name, double price, int quantity, String img) {
    this(null, type, name, price, quantity, img);
  }

  public Product( K id, String name, double price, int quantity) {
    this(id,  "Component", name, price, quantity, " ");
  }

  public Product(K id, String type, String name, double price, int quantity, String img) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.img = img;
  }

  public K getId() {
    return this.id;
  }

  public void setId(K id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
     this.price = price;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
 }

  public String getImg() {
    return this.img;
  }

  @Override
  public String toString() {
    return String.format("%s, %.2f, %d", this.name, this.price, this.quantity);
  }

  // @Override
  // public Product clone() {
  //   return new Product(this.id, this.name, this.price, this.quantity, this.img);
  // }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override 
  public boolean equals(Object obj) {
    if (!(obj instanceof Product))
      return false;

    return ((Product<Integer>) obj).getId() ==  this.getId();
  };
}
// == compares if two objects have the same reference in memory
// the deffault equals uses == if not iverriden
