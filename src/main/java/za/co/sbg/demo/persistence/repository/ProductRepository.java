package za.co.sbg.demo.persistence.repository;

import za.co.sbg.demo.persistence.entity.Product;

import java.util.List;

public interface ProductRepository {
    void addProduct(Product product);
    Product getProduct(Long productId);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(Product product);
}
