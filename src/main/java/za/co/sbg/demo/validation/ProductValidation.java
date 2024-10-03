package za.co.sbg.demo.validation;

import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.Product;

public class ProductValidation {
    public static void validateRequest(ProductRequest productRequest) {
        if(productRequest == null) {
            throw new ConstraintViolationException("Product request cannot be null");
        } else if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new ConstraintViolationException("Product name cannot be null or empty");
        } else if (productRequest.getPrice() < 0) {
            throw new ConstraintViolationException("Product price cannot be negative");
        }
    }

    public static void validateProduct(Product product) {
        if(product == null) {
            throw new ResourceNotFoundException("Product not found");
        }
    }
}
