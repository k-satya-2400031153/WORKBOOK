package com.inventory;

import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class App {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            // --- TASK 2: Add 5-8 dummy records (Only if table is nearly empty) ---
            Long count = (Long) session.createQuery("select count(p) from Product p").uniqueResult();
            if (count < 5) {
                System.out.println("--- Seeding Data ---");
                session.persist(new Product("Dell XPS 15", "Laptop", 1500.00, 5));
                session.persist(new Product("MacBook Air", "Laptop", 1200.00, 8));
                session.persist(new Product("Logitech Mouse", "Accessory", 60.00, 20));
                session.persist(new Product("Sony Headphones", "Accessory", 350.00, 0));
                session.persist(new Product("Samsung Monitor", "Electronics", 200.00, 12));
                session.persist(new Product("Mechanical Keyboard", "Accessory", 100.00, 15));
            }

            // --- TASK 3: SORTING (Order By) ---
            System.out.println("\n--- 3a. Sorted by Price (Ascending) ---");
            List<Product> asc = session.createQuery("from Product p order by p.price asc", Product.class).getResultList();
            display(asc);

            // --- TASK 4: SORT BY QUANTITY ---
            System.out.println("\n--- 4. Sorted by Quantity (Highest First) ---");
            List<Product> qty = session.createQuery("from Product p order by p.quantity desc", Product.class).getResultList();
            display(qty);

            // --- TASK 5: PAGINATION ---
            System.out.println("\n--- 5a. Page 1 (First 3 products) ---");
            Query<Product> q = session.createQuery("from Product p", Product.class);
            q.setFirstResult(0);
            q.setMaxResults(3);
            display(q.getResultList());

            System.out.println("\n--- 5b. Page 2 (Next 3 products) ---");
            q.setFirstResult(3);
            q.setMaxResults(3);
            display(q.getResultList());

            // --- TASK 6: AGGREGATES ---
            System.out.println("\n--- 6. Statistics ---");
            Long total = (Long) session.createQuery("select count(p) from Product p").uniqueResult();
            Double maxPrice = (Double) session.createQuery("select max(p.price) from Product p").uniqueResult();
            System.out.println("Total Products: " + total);
            System.out.println("Max Price: " + maxPrice);

            // --- TASK 7: GROUP BY ---
            System.out.println("\n--- 7. Count by Description (Category) ---");
            List<Object[]> groups = session.createQuery("select p.description, count(p) from Product p group by p.description", Object[].class).getResultList();
            for (Object[] row : groups) {
                System.out.println(row[0] + ": " + row[1]);
            }

            // --- TASK 9: LIKE FILTER ---
            System.out.println("\n--- 9. Products with name containing 'Mac' ---");
            List<Product> search = session.createQuery("from Product p where p.name like '%Mac%'", Product.class).getResultList();
            display(search);

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }

    private static void display(List<Product> list) {
        for (Product p : list) {
            System.out.println(p.getName() + " | $" + p.getPrice() + " | Qty: " + p.getQuantity());
        }
    }
}