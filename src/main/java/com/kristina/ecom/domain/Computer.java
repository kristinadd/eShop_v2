package com.kristina.ecom.domain;

import java.util.List;
public interface Computer {
  String getDescription();
  double getPrice();
  int getId();
  List<Product> getComponents();
}

// write a method to get the getComputer, get the base computer out, be part of the products list