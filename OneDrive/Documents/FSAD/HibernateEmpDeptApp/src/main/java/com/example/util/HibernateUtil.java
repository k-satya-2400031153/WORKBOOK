package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.example.entity.Employee;
import com.example.entity.Department;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure() // loads hibernate.cfg.xml
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Department.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}