package com.kristina.ecom.domain;

import java.util.Date;
import java.util.List;

public class ShoppingCart {
  private String id;
  private String user_id;
  private Date updated_at;
  private String status;
  private List<Computer<String>> computers;

  public ShoppingCart(String id, String user_id, Date updated_at, String status, List<Computer<String>> computers) {
    this.id = id;
    this.user_id = user_id;
    this.updated_at = updated_at;
    this.status = status;
    this.computers = computers;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return user_id;
  }

  public Date getUpdatedAt() {
    return updated_at;
  }

  public String getStatus() {
    return status;
  }

  public List<Computer<String>> getComputers() {
    return computers;
  }

}
