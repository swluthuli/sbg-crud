package za.co.sbg.demo.service.factory;

import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.persistence.entity.Product;

public class ProductFactory {

    public static Product createProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
    }
}
