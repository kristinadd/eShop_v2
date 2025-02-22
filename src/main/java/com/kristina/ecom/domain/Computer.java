package com.kristina.ecom.domain;

import java.util.List;
public interface Computer<K> {
  String getDescription();
  double getPrice();
  String getOrderID();
  List<Product<K>> getComponents();
}
