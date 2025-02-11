package com.kristina.ecom.console.mongo;

import java.io.IOException;
import java.util.Properties;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDataSourceFactory {
  private static MongoDataSourceFactory instance = new MongoDataSourceFactory("db.properties");

  private Properties props;

  private  MongoDataSourceFactory(String fileName) {
    props = new Properties();
    try {
      props.load(getClass().getClassLoader().getResourceAsStream("finaName"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }

  public static MongoDataSourceFactory getInstance() {
    return instance;
  }

  public  MongoDatabase getDatabase() {
    return MongoClients.create(props.getProperty("MONGO_URL"))
      .getDatabase(props.getProperty("MONGO_DB"));
  }
}
