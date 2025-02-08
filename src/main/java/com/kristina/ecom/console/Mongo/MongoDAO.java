package com.kristina.ecom.console.Mongo;
import com.kristina.ecom.domain.Product;

public interface MongoDAO {
  public Product read(int id);
  public boolean create(Product product);
}
