package com.klu.productmodule.repository;

import com.klu.productmodule.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived Query Methods
    List<Product> findByProductCategory(String targetCategory);
    List<Product> findByProductPriceBetween(double minimumPrice, double maximumPrice);

    // JPQL Queries - Notice the fields are now p.productPrice and p.productCategory
    @Query("SELECT p FROM Product p ORDER BY p.productPrice ASC")
    List<Product> sortProductsByPriceJPQL();

    @Query("SELECT p FROM Product p WHERE p.productPrice > :price")
    List<Product> findExpensiveProductsJPQL(@Param("price") double price);

    @Query("SELECT p FROM Product p WHERE p.productCategory = :category")
    List<Product> findByCategoryJPQL(@Param("category") String category);
}