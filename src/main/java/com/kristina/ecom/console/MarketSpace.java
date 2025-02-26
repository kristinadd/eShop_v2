package com.kristina.ecom.console;

import java.util.Scanner;

import com.kristina.ecom.domain.ShoppingCart;
import com.kristina.ecom.domain.Status;
import com.kristina.ecom.dao.DAOException;
import com.kristina.ecom.dao.ShoppingCartDAOMongo;
import com.kristina.ecom.domain.Component;
import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.ComputerBase;
import com.kristina.ecom.domain.Product;
import com.kristina.ecom.service.ProductService;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class MarketSpace {
  private static  MarketSpace instance = new MarketSpace();
  private Map<Integer, Product<Integer>> products;
  private List<Computer<String>> cart;


  private MarketSpace() {
    products = new HashMap<>();
    cart = new ArrayList<>();
  }

  public static MarketSpace instance() {
    return instance;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void buy() {

    Computer<String> computer = new ComputerBase<String>();
    Boolean cancel = false;
    Scanner sc = new Scanner(System.in);
    int c = 0;

    while (true) {
      System.out.printf("Current Build: %s, and total price is %.2f\n", computer.getDescription(), computer.getPrice());
      System.out.println("What component would you like to add?");
      menu();

      c = sc.nextInt();
      if (c == -1) {
        cancel = true;
        break;
      } 
      
      if (c == 0)
        break;

      if  (products.keySet().contains(c)) {
        Product<Integer> product = products.get(c);

        if (product.getQuantity() == 0) {
          System.out.println("Out of stock. Select another product.");

        } else {
          Product<Integer> p = new Product<Integer>();
          try {
            p = (Product) product.clone();
            p.setQuantity(1);
          } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
          } 
          Product<String> productMongo = new Product<>();
          productMongo.setId(p.getId().toString());
          productMongo.setType(p.getType());
          productMongo.setName(p.getName());
          productMongo.setPrice(p.getPrice());
          productMongo.setImg(p.getImg());
          productMongo.setQuantity(p.getQuantity());


          computer = new Component<String>(computer, productMongo);
          product.setQuantity(product.getQuantity() - 1);
        }
      } else {
        System.out.println("Invalid choice. Please try again.");
        continue;
      }
    }
    // sc.close();

    if (!cancel) {
      cart.add(computer);
      // TBC: persists cart to MongoDB
      // ShoppingCartDAOMongo.create(computer);
      // persist to the database
      ShoppingCartDAOMongo shopDao = new ShoppingCartDAOMongo();
      ShoppingCart shoppingCart = new ShoppingCart(computer.getOrderID(), "98765", new Date(), Status.ACTIVE, cart);
      try {
        
      shopDAO.create(shoppingCart);
      } catch (DAOException ex) {

      }
      } else {
        System.out.println("Order is canceled!");
    }
  }

  private void menu() {
    System.out.println("I'm in the MarketSpace class!");

    products.forEach((k,v) -> System.out.println(k + ":" + v ));
    System.out.println(-1 + ": " + "Cancel");
    System.out.println(0 + ": " + "Done");
  }

  public List<Computer<String>> getCart() {
    return cart;
  } // check the main
}
