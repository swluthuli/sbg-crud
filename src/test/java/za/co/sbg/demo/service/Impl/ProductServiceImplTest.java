package za.co.sbg.demo.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.Product;
import za.co.sbg.demo.persistence.repository.ProductRepository;
import za.co.sbg.demo.service.factory.ProductFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactory productFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProductList = Arrays.asList(
                Product.builder().id(1L).name("Product1").price(100.0).build(),
                Product.builder().id(2L).name("Product2").price(200.0).build()
        );
        when(productRepository.getAllProducts()).thenReturn(mockProductList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        Product mockProduct = Product.builder().id(1L).name("Product1").price(100.0).build();
        when(productRepository.getProduct(1L)).thenReturn(mockProduct);

        // Act
        Product result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).getProduct(1L);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        ProductRequest productRequest = ProductRequest.builder().name("Product1").price(100.0).build();
        Product product = Product.builder().id(1L).name("Product1").price(100.0).build();

        doNothing().when(productRepository).addProduct(product);
        when(productFactory.createProduct(productRequest)).thenReturn(product);

        // Act
        productService.createProduct(productRequest);

        // Assert
        verify(productRepository, times(1)).addProduct(product);
        verify(productFactory, times(1)).createProduct(productRequest);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Product existingProduct = Product.builder().id(1L).name("Old Product").price(100.0).build();
        ProductRequest updateRequest = ProductRequest.builder().name("Updated Product").price(150.0).build();

        when(productRepository.getProduct(1L)).thenReturn(existingProduct);

        // Act
        boolean result = productService.updateProduct(1L, updateRequest);

        // Assert
        assertTrue(result);
        assertEquals("Updated Product", existingProduct.getName());
        assertEquals(150.0, existingProduct.getPrice());
        verify(productRepository, times(1)).updateProduct(existingProduct);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Product existingProduct = Product.builder().id(1L).name("Product1").price(100.0).build();
        when(productRepository.getProduct(1L)).thenReturn(existingProduct);

        // Act
        boolean result = productService.deleteProduct(1L);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).deleteProduct(existingProduct);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        when(productRepository.getProduct(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, never()).deleteProduct(any(Product.class));
    }
}
