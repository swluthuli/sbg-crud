package za.co.sbg.demo.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.Product;
import za.co.sbg.demo.persistence.repository.ProductRepository;
import za.co.sbg.demo.service.ProductService;
import za.co.sbg.demo.service.factory.ProductFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

import static za.co.sbg.demo.validation.ProductValidation.validateProduct;
import static za.co.sbg.demo.validation.ProductValidation.validateRequest;

@RequestScoped
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Inject
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProduct(id);
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        logger.info("Creating product {}", productRequest.toString());
        validateRequest(productRequest);
        Product product = ProductFactory.createProduct(productRequest);
        logger.info("Product created: {}", product.toString());
     productRepository.addProduct(product);
    }

    @Override
    public boolean updateProduct(Long id, ProductRequest productRequest) {
        logger.info("Product update request: {}", productRequest.toString());
        Product product = getProductById(id);
        validateProduct(product);

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.updateProduct(product);
        logger.info("Product updated: {}", product);
        return true;
    }

    @Override
    public boolean deleteProduct(Long id) {
        logger.info("Product delete request: {}", id);
        Product product = getProductById(id);
        validateProduct(product);
        productRepository.deleteProduct(product);
        logger.info("Product deleted: {}", product);
        return true;
    }
}
