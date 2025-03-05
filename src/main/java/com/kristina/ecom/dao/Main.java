package com.kristina.ecom.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.kristina.ecom.domain.ShoppingCart;
import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.ComputerBase;
import com.kristina.ecom.domain.Product;
import com.kristina.ecom.domain.Status;
public class Main {
  public static void main(String[] args) {
    // PRODUCT DAO
    OrderDAOMongo orderDao = new OrderDAOMongo();
    ProductDAOMongo productDao = new ProductDAOMongo();


    // try {
    //   int result7 = productDao.delete("67adc74748bdac22c0bfba99");
    //   System.out.println("🍋 " + result7);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }


    // Product product3 = new Product("", "Component", "Monitor", 30.50, 10, "monitor.img");
    // try {
    //   Product product = productDao.create(product3);
    //   System.out.println("✅ " + product);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    //   // System.out.println(ex.getCause());
    // }
    //                                                               // READ A PRODUCT 
    // try {
    //   Product product = productDao.read("67ac63ac2a325047a6d12097");
    //   System.out.println("🦁 " + product);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                          // ORDER TESTS
    // // OrderDAOMongo orderDao = new OrderDAOMongo();

    //                                             // CREATE AN ORDER
    // List<Product> products = new ArrayList<>();

    // Order order = new Order(
    //   "Order with generated id",
    //   70.99f,
    //   LocalDateTime.now(),
    //   products
    // );

    // try {
    //   Order order2 = orderDao.create(order);
    //   System.out.println("🆕 " + order2);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                               // READ ALL ORDERS
    // try {
    //   List<Order > orders = orderDao.readAll();
    //   for (Order  order3 : orders) {
    //     System.out.println("⭐️ Real all orders: " +  order3);
    //   }
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                 // READ ONE ORDER
    // try {
    //   Order  order4 = orderDao.read("67acbba2c6e95a55d2762bb3");
    //   System.out.println("🍊 Order details " + order4);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                   // UPDATE AN ORDER 
    //   try {
    //     Order  order5 = orderDao.read("67ae11fdbda9e3474bfa5603");
    //     order5.setDescription("NEW UPDATED order");
    //     int result = orderDao.update(order5);
    //     System.out.println("👻 Made " + result + " updates.");
    //   } catch (DAOException ex) {
    //     ex.printStackTrace();
    //   }
                                                        // DELETE ORDER
      // try {
      //   int result = orderDao.delete("67adc829df98d6064d86a500");
      //   System.out.println("❌ Deleted " + result + " orders.");
      // } catch (DAOException ex) {
      //   ex.printStackTrace();
      // }


      // DELETE MANY ORDERS ONE BY ONE
      //  try {
      //   List<Order > orders = orderDao.readAll();

      //   for (Order  order3 : orders) {
      //     try {
      //       int result = orderDao.delete(order3.getId());
      //       System.out.println("❌ Deleted: " + result);  
      //     } catch (DAOException ex) {
      //       ex.printStackTrace();
      //     }
      //   }
      // } catch (DAOException ex) {
      //   ex.printStackTrace();
      // }


      // CREATE A SHOPPING CART

      ShoppingCartDAOMongo shopDao = new ShoppingCartDAOMongo();

      Product product3 = new Product(123, "Component", "Monitor", 30.50, 10, "monitor.img");
      Product product4 = new Product(345, "Component", "Mouse", 30.50, 10, "mouse.img");
      Product product5 = new Product(567, "Component", "Keyboard", 30.50, 10, "keyboard.img");

      List<Product> products = new ArrayList<>();

      products.add(product3);
      products.add(product4);
      products.add(product5);

      Computer computer = new ComputerBase("11111111111", products);
      Computer computer_2 = new ComputerBase("2222222", products);
      List<Computer> computers = new ArrayList<>();
      computers.add(computer);
      computers.add(computer_2);


      ShoppingCart shoppingCart = new ShoppingCart("12345", "98765", new Date(), Status.ACTIVE, computers);
      
      try {
        shopDao.create(shoppingCart);
      } catch (DAOException ex) {
        ex.printStackTrace();
      }


      // read a shopping cart

      // try {
      //   Computer computer2 = shopDao.read("67beb520dc65d303dd3e6bbb");
      //   System.out.println(computer2);
      // } catch (DAOException ex) {
      //   ex.printStackTrace();
      // }


      // DELETE SHOPPING CART
      // try {
      //   shopDao.delete("67baf469a707cb1b7f865119");
      // } catch (DAOException ex) {
      //   ex.printStackTrace();
      // }


      // READ ALL CARTS FROM DB

      
  }
}
