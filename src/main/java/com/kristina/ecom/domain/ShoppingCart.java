package com.kristina.ecom.domain;

import java.util.Date;
import java.util.List;

public class ShoppingCart {
  private String id;
  private String user_id;
  private Date updated_at;
  private Status status; 
  private List<Computer> computers;

  public ShoppingCart(String id, String user_id, Date updated_at, Status status, List<Computer> computers) {
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

  public Status getStatus() {
    return status;
  }

  public List<Computer> getComputers() {
    return computers;
  }

  @Override
  public String toString() {
    return String.format(
      "🛍️ ShoppingCart\n id: %s\n user_id: %s\n updated_at: %s\n status: %s\n computers: %s\n",
      id, user_id, updated_at, status, computers
    );
  }  
}
