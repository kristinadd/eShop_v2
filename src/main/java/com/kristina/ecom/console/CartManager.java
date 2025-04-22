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
import com.kristina.ecom.domain.Status;
import com.kristina.ecom.service.OrderService;
import com.kristina.ecom.service.ShoppingCartService;

public class CartManager {
  private Scanner sc;
  private ShoppingCart shoppingCart;
  private SortStrategy strategy, sortByOrderIDStrategy, sortByPriceStrategy;
  private ShoppingCartService shopService = new ShoppingCartService();


  public CartManager(ShoppingCart shoppingCart) {
    sc = new Scanner(System.in);
    this.shoppingCart = shoppingCart;
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
          checkOut();
          break;
        case 3:
          return;
        case 4:
          System.out.println("Provide a shopping cart id: ");
          String input = sc.nextLine();
          getCart(input);
          break;
        case 5:
          System.out.println("Provide a shopping cart id: ");
          input = sc.nextLine();
          editShoppingCart(input);
          break;
        case 6:
          System.out.println("Provide a shopping cart id: ");
          input = sc.nextLine();
          delete(input);
          break;
        case 7:
          cancel();
          break;
        default:
          System.out.println("❌ Invalid choice. Please try again.");
      }
    }
  }

  private void menu() {
    String[] items = {
      "See all shopping carts",
      "Check out",
      "Return to main menu",
      "See specific shopping cart",
      "Edit shopping cart",
      "Delete shopping cart",
      "Cancel shopping cart"
    };

    System.out.println("Shopping Cart details: ");

    for (int i = 0; i< items.length; i++) {
      System.out.printf("%d: %s\n", i+1, items[i]);
    }
  }

  public void sort(String key) {
    if (shoppingCart.getComputers().isEmpty()) {
      System.out.println("No items");
      return;
    }
    if (key.equals("ID"))
      this.strategy = this.sortByOrderIDStrategy;
    else if (key.equals("PRICE"))
      this.strategy = this.sortByPriceStrategy;

    this.strategy.sort(shoppingCart.getComputers());
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
    cart = shopService.readId(id);

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

    List<Computer> computers = shoppingCart.getComputers();
    for (Computer computer : computers) {
      Order order = new Order(computer);
      service.create(order);
    }
    shoppingCart.setStatus(Status.COMPLETED);
    shopService.update(shoppingCart);
    shoppingCart.getComputers().clear();
    shoppingCart.setStatus(Status.NEW);
  }
  
  public void editShoppingCart(String id) {
    // what does it mean to edit the shoppingCart?
    // remove products
    // increase quantity
    // decrease quantity
    // change the status
    // add a product
    // remove a product
  }

  public int delete(String id) {
    shoppingCart.getComputers().clear();
    shoppingCart.setStatus(Status.NEW);
    return shopService.delete(id);
  }

  public void cancel() {
    shopService.cancel(shoppingCart);
    shoppingCart.getComputers().clear();
    shoppingCart.setStatus(Status.NEW);
  }
}
