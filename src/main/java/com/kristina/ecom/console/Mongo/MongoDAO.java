package com.kristina.ecom.console.Mongo;

public interface MongoDAO<T> {
  public T get(int id);
  public boolean create(T t);
  public T update(T t);
  public boolean delete(int id);
}
