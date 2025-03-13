package com.kristina.ecom.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.ShoppingCart;
import com.kristina.ecom.domain.SortByOrderID;
import com.kristina.ecom.domain.SortByPrice;
import com.kristina.ecom.domain.SortStrategy;
import com.kristina.ecom.service.OrderService;
import com.kristina.ecom.service.ShoppingCartService;

public class CartManager {
  private Scanner sc;
  private List<Computer> cart;
  private SortStrategy strategy, sortByOrderIDStrategy, sortByPriceStrategy;
  private ShoppingCartService shopService = new ShoppingCartService();


  public CartManager(List<Computer> cart) {
    sc = new Scanner(System.in);
    this.cart = cart;
    sortByOrderIDStrategy = new SortByOrderID();
    sortByPriceStrategy = new SortByPrice();
  }

  public void admin() {
    while (true) {
      menu();
      int c = sc.nextInt();
      sc.nextLine();  // Consume the leftover newline
      switch (c) {
        case 1:
          getCarts();
          break;
        case 2:
          sort("ID");
          break;
        case 3:
        sort("PRICE");
          break;
        case 4:
          checkOut();
          break;
        case 5:
          return;
        case 6:
          System.out.println("Provide a shopping cart id: ");
          String input = sc.nextLine();
          getCart(input);
          break;
        case 7:
          System.out.println("Provide a shopping cart id: ");
          input = sc.nextLine();
          editShoppingCart(input);
          break;
        case 8:
          System.out.println("Provide a shopping cart id: ");
          input = sc.nextLine();
          delete(input);
          break;
        default:
          System.out.println("❌ Invalid choice. Please try again.");
      }
    }
  }

  private void menu() {
    String[] items = {
      "See all shopping carts",
      "Sort by order ID (Descending order)",
      "Sort by order price (Descending order)",
      "Check out",
      "Return to main menu",
      "See specific shopping cart",
      "Edit shopping cart",
      "Delete shopping cart"
    };

    System.out.println("Shopping Cart details: ");

    for (int i = 0; i< items.length; i++) {
      System.out.printf("%d: %s\n", i+1, items[i]);
    }
  }

  public void sort(String key) {
    if (cart.isEmpty()) {
      System.out.println("No items");
      return;
    }
    if (key.equals("ID"))
      this.strategy = this.sortByOrderIDStrategy;
    else if (key.equals("PRICE"))
      this.strategy = this.sortByPriceStrategy;

    this.strategy.sort(cart);
  }

  public void getCarts() {
    List<ShoppingCart> carts = new ArrayList<>();
    carts = shopService.readAll();

    if (carts.isEmpty())
      System.out.println("❌ No available shopping carts");
    else
    for (ShoppingCart cart : carts) {
      System.out.println(cart);
    }
  }

  public ShoppingCart getCart(String id) {
    ShoppingCart cart;
    cart = shopService.read(id);

    if (cart == null) {
      System.out.println("❌ Coudn't find the shopping cart with id: " + id);
      return null;
    } else {
      System.out.println(cart);
      return cart;
    }
  }

  public void checkOut() {
    OrderService service = new OrderService();

    for (Computer computer : cart) {
      Order order = new Order(computer);
      service.create(order);
    }
  }

  // later
  public void editShoppingCart(String id) {
    
  }

  public int delete(String id) {
    return shopService.delete(id);
  }
}
