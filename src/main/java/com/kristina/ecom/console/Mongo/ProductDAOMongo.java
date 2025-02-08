package com.kristina.ecom.console.Mongo;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;

public class ProductDAOMongo implements MongoDAO{
  private MongoDataSource dataSourceFactory;
  private MongoCollection<Document> collection;

  public ProductDAOMongo() {
    this.dataSourceFactory = MongoDataSource.getInstance();
    this.collection = dataSourceFactory.getDatabase().getCollection("products");
  }
  
  @Override
  public int read(int id) {
    Bson fields = Projections.fields(
      Projections.include("type", "name", "price")
    );

    Document document = collection.find(eq("_id", id))
    .projection(fields)
    .first();

    if (document != null) {
      System.out.println(document.toJson());
    }

    return id;
  }
  
}
