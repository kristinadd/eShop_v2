package com.kristina.ecom.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByPrice<K> implements SortStrategy<K> {

  @Override 
  public void sort(List<Computer<K>> cart) {
    Comparator<Computer<K>> comparator = new Comparator<>() {
      @Override 
      public int compare(Computer<K> c1, Computer<K> c2) {
        return (int) (c2.getPrice() - c1.getPrice());
      }
    };
    Collections.sort(cart, comparator);
  }
}
