package com.kristina.ecom.dao;

import org.bson.Document;

import com.kristina.ecom.domain.Product;

public class UtilDAOMongo {

  public static Product<String> toProduct(Document document) {
    if (document == null)
      return null;

    Product<String> product = new Product<String>(
      // document.getObjectId("_id").toString(),
      document.getString("_id"),
      document.getString("type"),
      document.getString("name"),
      document.getDouble("price"),
      document.getInteger("quantity"),
      document.getString("image")
      );
    return product;
  }

  public static Document toDocument(Product<String> product) {
  if (product != null) {
  Document document = new Document();
  // isEmpty throws NullPointerException if product.getId() is null
    if (!product.getId().isEmpty())
      document.append("_id", product.getId());
      document.append("type", product.getType());
      document.append("name", product.getName());
      document.append("price", product.getPrice());
      document.append("quantity", product.getQuantity());
      document.append("image", product.getImg());

    return document;
  } else {
    System.out.println("Can't convert to document. Product is either null or invalid.");
  }
  return null;
  }
}
