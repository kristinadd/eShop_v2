package com.kristina.ecom.console.mongo;

import com.mongodb.MongoException;
import java.util.List;

public interface MongoDAO<K, V> {
  public int create(V v) throws MongoException;
  public V get(int id);
  public V update(V t);
  public boolean delete(int id);
}
