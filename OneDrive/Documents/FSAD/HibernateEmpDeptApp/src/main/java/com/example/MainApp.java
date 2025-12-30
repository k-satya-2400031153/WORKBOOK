package com.example;

import java.util.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import com.example.entity.*;
import com.example.util.HibernateUtil;

public class MainApp {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int ch;
        do {
            System.out.println("\n=== HQL MENU ===");
            System.out.println("1. Insert Sample Data");
            System.out.println("2. Select - Show All Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Sort Employees");
            System.out.println("6. Aggregate Functions");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            ch = sc.nextInt();

            switch(ch) {
                case 1: insertSampleData(); break;
                case 2: showEmployees(); break;
                case 3: updateSalary(); break;
                case 4: deleteEmployee(); break;
                case 5: sortEmployees(); break;
                case 6: aggregate(); break;
            }
        } while(ch != 0);

        HibernateUtil.getSessionFactory().close();
    }

    static void insertSampleData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Department d1 = new Department("HR");
        Department d2 = new Department("IT");

        session.persist(d1);
        session.persist(d2);

        session.persist(new Employee("Mahesh", 30000, d1));
        session.persist(new Employee("Sita", 45000, d2));
        session.persist(new Employee("Ravi", 50000, d2));

        tx.commit();
        session.close();
        System.out.println("Sample data inserted!");
    }

    static void showEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> list = session.createQuery("from Employee", Employee.class).list();
        System.out.println("\nEMPID | NAME | SALARY | DEPT");
        for (Employee e : list) {
            System.out.println(e.getEmpId() + " | " + e.getName() + " | " +
                    e.getSalary() + " | " + e.getDept().getDeptName());
        }
        session.close();
    }

    static void updateSalary() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        System.out.print("Enter new salary: ");
        double sal = sc.nextDouble();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("update Employee set salary=?1 where empId=?2");
        q.setParameter(1, sal);
        q.setParameter(2, id);

        int updated = q.executeUpdate();
        tx.commit();
        session.close();
        System.out.println(updated + " record updated!");
    }

    static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("delete from Employee where empId=:id");
        q.setParameter("id", id);

        int deleted = q.executeUpdate();
        tx.commit();
        session.close();
        System.out.println(deleted + " record deleted!");
    }

    static void sortEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> list = session.createQuery("from Employee order by salary desc", Employee.class).list();

        System.out.println("\n-- Employees Sorted by Salary (DESC) --");
        for(Employee e : list) {
            System.out.println(e.getName() + " -> Rs." + e.getSalary());
        }
        session.close();
    }

    static void aggregate() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query count = session.createQuery("select count(*) from Employee");
        Query avg = session.createQuery("select avg(salary) from Employee");
        Query sum = session.createQuery("select sum(salary) from Employee");

        System.out.println("\nTotal Employees: " + count.uniqueResult());
        System.out.println("Average Salary: " + avg.uniqueResult());
        System.out.println("Total Salary Paid: " + sum.uniqueResult());
        session.close();
    }
}