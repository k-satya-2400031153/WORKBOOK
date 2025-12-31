package com.inventory;

import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        try {
            System.out.println("--- Creating new products ---");
            createProduct(factory, new Product("Gaming Laptop", "High-end specs", 1200.00, 10));
            createProduct(factory, new Product("Wireless Mouse", "Ergonomic", 25.50, 50));
            System.out.println("\n--- Reading product with ID 1 ---");
            readProduct(factory, 1L);
            System.out.println("\n--- Updating product with ID 1 ---");
            updateProduct(factory, 1L, 999.99);


            System.out.println("\n--- Deleting product with ID 2 ---");
            deleteProduct(factory, 2L);

        } finally {
            factory.close();
        }
    }

    // Helper Method to CREATE
    public static void createProduct(SessionFactory factory, Product product) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
            System.out.println("Saved: " + product.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readProduct(SessionFactory factory, Long id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Product p = session.get(Product.class, id);
            if (p != null) {
                System.out.println("Found: " + p);
            } else {
                System.out.println("Product not found!");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateProduct(SessionFactory factory, Long id, double newPrice) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Product p = session.get(Product.class, id);
            if (p != null) {
                p.setPrice(newPrice);
                System.out.println("Updated price for: " + p.getName());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteProduct(SessionFactory factory, Long id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Product p = session.get(Product.class, id);
            if (p != null) {
                session.remove(p);
                System.out.println("Deleted product: " + p.getName());
            } else {
                System.out.println("Product not found, cannot delete.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}