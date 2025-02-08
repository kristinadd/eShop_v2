package com.kristina.ecom.console.Mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDataSourceFactory {
  private static MongoClient mongo;
  private static MongoDatabase database;

  private  MongoDataSourceFactory() {
    MongoDataSourceFactory.mongo = MongoClients.create("mongodb://127.0.1:27017");
    MongoDataSourceFactory.database = mongo.getDatabase("ecom");
  }

  private static MongoDataSourceFactory instance = new MongoDataSourceFactory();

  public static MongoDataSourceFactory getInstance() {
    return instance;
  }

  public  MongoDatabase getDatabase() {
    return database;
  }
}
