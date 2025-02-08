package com.kristina.ecom.console.Mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDataSource {
  private static MongoClient mongo;
  private static MongoDatabase database;

  private  MongoDataSource() {
    MongoDataSource.mongo = MongoClients.create("mongodb://127.0.1:27017");
    MongoDataSource.database = mongo.getDatabase("ecom");
  }

  private static MongoDataSource instance = new MongoDataSource();

  public static MongoDataSource getInstance() {
    return instance;
  }

  public  MongoDatabase getDatabase() {
    return database;
  }
}
