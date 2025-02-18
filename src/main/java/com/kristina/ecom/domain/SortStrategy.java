package com.kristina.ecom.domain;

import java.util.List;

public interface SortStrategy<K> {
  void sort(List<Computer<K>> cart);
}
