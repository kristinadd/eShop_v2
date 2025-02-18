package com.kristina.ecom.domain;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class SortByOrderID<K> implements SortStrategy<K> {

  @Override 
  public void sort(List<Computer<K>> cart) {

    Comparator<Computer<K>> comparator = new Comparator<>() {
      
      @Override 
      public int compare(Computer<K> c1, Computer<K> c2) {
        return c2.getOrderID().compareTo(c1.getOrderID());
      }
    };
    Collections.sort(cart, comparator);
  }
}
