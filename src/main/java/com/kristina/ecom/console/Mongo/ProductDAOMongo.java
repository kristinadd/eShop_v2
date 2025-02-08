package com.kristina.ecom.console.Mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ProductDAOMongo implements MongoDAO{
  private MongoDataSourceFactory dataSourceFactory;
  private MongoCollection collection;

  public ProductDAOMongo() {
    this.dataSourceFactory = MongoDataSourceFactory.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("products");
  }
  
  @Override
  public int read(int id) {
    System.out.println(collection.getNamespace());
    return id;
  }
  
}
