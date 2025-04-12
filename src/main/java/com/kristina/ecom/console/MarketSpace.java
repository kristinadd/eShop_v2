package com.kristina.ecom.console;

import java.util.Scanner;

import com.kristina.ecom.domain.ShoppingCart;
import com.kristina.ecom.domain.Status;
import com.kristina.ecom.service.ProductService;
import com.kristina.ecom.service.ShoppingCartService;
import com.kristina.ecom.domain.Component;
import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.ComputerBase;
import com.kristina.ecom.domain.Product;

import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import org.bson.types.ObjectId;

public class MarketSpace {
  private static  MarketSpace instance = new MarketSpace();
  private Map<Integer, Product> products;
  private ShoppingCart shoppingCart;
  private ShoppingCartService shopService;
 

  private MarketSpace() {
    products = new HashMap<>();
    shopService = new ShoppingCartService();
    this.shoppingCart = shopService.read("98765");
    if (shoppingCart == null )
      shoppingCart = new ShoppingCart(new ObjectId().toHexString(), "98765", new Date(), Status.NEW, new ArrayList<>());
  }

  public static MarketSpace instance() {
    return instance;
  }

  public void buy() {
    new ProductService().getAll().forEach((product) -> this.products.put(product.getId(), product));
    Computer computer = new ComputerBase();
    ComputerBase clonedComputer = new ComputerBase();
    

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

      if  (products.keySet().contains(c)) { // products.containsKey(c) // more optimal
        Product product = products.get(c);

        if (product.getQuantity() == 0) {
          System.out.println("Out of stock. Select another product.");

        } else {
          Product p = new Product();
          try {
            p = (Product) product.clone();
            p.setQuantity(1);
          } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
          } 
          
          computer = new Component(computer, p); // decorator wrapping the computer
          product.setQuantity(product.getQuantity() - 1);
        }
      } else {
        System.out.println("Invalid choice. Please try again.");
        continue;
      }
    }

    if (!cancel) {
      shoppingCart.getComputers().add(computer);
      if (shoppingCart.getStatus() == Status.NEW) {
        shopService.create(shoppingCart);
      } else if (shoppingCart.getStatus() == Status.ACTIVE) {
        shopService.update(shoppingCart);
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

  public ShoppingCart getCart() {
    return shoppingCart;
  }
}
