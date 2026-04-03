package com.klu.productmodule.controller;

import com.klu.productmodule.model.Product;
import com.klu.productmodule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Helper Endpoint: Database mein products daalne ke liye
    @PostMapping("/add-multiple")
    public String addMultipleProducts(@RequestBody List<Product> products) {
        productRepository.saveAll(products);
        return products.size() + " Products added successfully!";
    }

    // Task 4a. /products/category/{category} (Derived Query test)
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        // UPDATED: Calling findByProductCategory instead of findByCategory
        return productRepository.findByProductCategory(category);
    }

    // Task 4b. /products/filter?min=&max= (Derived Query test)
    @GetMapping("/filter")
    public List<Product> getByPriceRange(@RequestParam double min, @RequestParam double max) {
        // UPDATED: Calling findByProductPriceBetween instead of findByPriceBetween
        return productRepository.findByProductPriceBetween(min, max);
    }

    // Task 4c. /products/sorted (JPQL Query test)
    @GetMapping("/sorted")
    public List<Product> getSortedProducts() {
        return productRepository.sortProductsByPriceJPQL();
    }

    // Task 4d. /products/expensive/{price} (JPQL Query test)
    @GetMapping("/expensive/{price}")
    public List<Product> getExpensiveProducts(@PathVariable double price) {
        return productRepository.findExpensiveProductsJPQL(price);
    }
}