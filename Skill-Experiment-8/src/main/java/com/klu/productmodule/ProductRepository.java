package com.klu.productmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2. Derived Query Methods (Spring automatically method ke naam se query bana lega)
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(double min, double max);

    // 3. JPQL Queries (Custom queries database se baat karne ke liye)
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortProductsByPriceJPQL();

    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> findExpensiveProductsJPQL(@Param("price") double price);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategoryJPQL(@Param("category") String category);
}