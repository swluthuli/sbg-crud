package za.co.sbg.demo.service;

import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.persistence.entity.Product;

import java.util.List;

public interface ProductService {

     List<Product> getAllProducts();
     Product getProductById(Long id);
     void createProduct(ProductRequest productDTO);
     boolean updateProduct(Long id, ProductRequest productDTO);
     boolean deleteProduct(Long id);

}
