package com.kristina.ecom.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kristina.ecom.domain.Computer;
import com.kristina.ecom.domain.ComputerBase;
import com.kristina.ecom.domain.Order;
import com.kristina.ecom.domain.Product;
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


    // Product<String> product3 = new Product<String>("", "Component", "Monitor", 30.50, 10, "monitor.img");
    // try {
    //   Product<String> product = productDao.create(product3);
    //   System.out.println("✅ " + product);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    //   // System.out.println(ex.getCause());
    // }
    //                                                               // READ A PRODUCT 
    // try {
    //   Product<String> product = productDao.read("67ac63ac2a325047a6d12097");
    //   System.out.println("🦁 " + product);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                          // ORDER TESTS
    // // OrderDAOMongo orderDao = new OrderDAOMongo();

    //                                             // CREATE AN ORDER
    // List<Product<String>> products = new ArrayList<>();

    // Order<String> order = new Order<String>(
    //   "Order with generated id",
    //   70.99f,
    //   LocalDateTime.now(),
    //   products
    // );

    // try {
    //   Order<String> order2 = orderDao.create(order);
    //   System.out.println("🆕 " + order2);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                               // READ ALL ORDERS
    // try {
    //   List<Order<String> > orders = orderDao.readAll();
    //   for (Order<String>  order3 : orders) {
    //     System.out.println("⭐️ Real all orders: " +  order3);
    //   }
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                 // READ ONE ORDER
    // try {
    //   Order<String>  order4 = orderDao.read("67acbba2c6e95a55d2762bb3");
    //   System.out.println("🍊 Order details " + order4);
    // } catch (DAOException ex) {
    //   ex.printStackTrace();
    // }
    //                                                   // UPDATE AN ORDER 
    //   try {
    //     Order<String>  order5 = orderDao.read("67ae11fdbda9e3474bfa5603");
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
      //   List<Order<String> > orders = orderDao.readAll();

      //   for (Order<String>  order3 : orders) {
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

      Product<String> product3 = new Product<String>("", "Component", "Monitor", 30.50, 10, "monitor.img");
      Product<String> product4 = new Product<String>("", "Component", "Mouse", 30.50, 10, "mouse.img");
      Product<String> product5 = new Product<String>("", "Component", "Keyboard", 30.50, 10, "keyboard.img");

      List<Product<String>> products = new ArrayList<>();

      products.add(product3);
      products.add(product4);
      products.add(product5);

      Computer<String> computer = new ComputerBase<String>("11111111111", products);
      
      try {
        shopDao.create(computer);
      } catch (DAOException ex) {
        ex.printStackTrace();
      }


      // read a shopping cart

      try {
        Computer<String> computer2 = shopDao.read("67ba13c75127ab6ef6e89b72");
        System.out.println(computer2);
      } catch (DAOException ex) {
        ex.printStackTrace();
      }

      try {
        shopDao.delete("67baedeac6587d068b914fd1");
      } catch (DAOException ex) {
        ex.printStackTrace();
      }

  }
}
