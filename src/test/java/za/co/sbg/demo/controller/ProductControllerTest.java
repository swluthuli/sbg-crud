package za.co.sbg.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.ProductRequest;
import za.co.sbg.demo.persistence.entity.Product;
import za.co.sbg.demo.service.ProductService;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProductList = Arrays.asList(
                Product.builder().id(1L).name("Product1").price(12.0).build(),
                Product.builder().id(2L).name("Product2").price(32.0).build()
        );
        when(productService.getAllProducts()).thenReturn(mockProductList);

        // Act
        List<Product> products = productController.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        Product mockProduct = Product.builder().id(1L).name("Product1").price(12.0).build();
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        // Act
        Product product = productController.getProductById(1L);

        // Assert
        assertNotNull(product);
        assertEquals("Product1", product.getName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productService.getProductById(1L)).thenReturn(null);

        // Act
        Product product = productController.getProductById(1L);

        // Assert
        assertNull(product);
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testAddProduct() {
        // Arrange
        ProductRequest mockRequest = ProductRequest.builder()
                .name("New Product")
                .price(10.0)
                .build();

        // Act
        Response response = productController.addProduct(mockRequest);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(mockRequest, response.getEntity());
        verify(productService, times(1)).createProduct(mockRequest);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        ProductRequest mockRequest = ProductRequest.builder()
                .name("Updated Product")
                .price(15.0)
                .build();

        when(productService.updateProduct(1L, mockRequest)).thenReturn(true);

        // Act
        Response response = productController.updateProduct(mockRequest, 1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(productService, times(1)).updateProduct(1L, mockRequest);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        ProductRequest mockRequest = ProductRequest.builder()
                .name("Updated Product")
                .price(15.0)
                .build();

        when(productService.updateProduct(1L, mockRequest)).thenReturn(false);

        // Act
        Response response = productController.updateProduct(mockRequest, 1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(productService, times(1)).updateProduct(1L, mockRequest);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Act
        Response response = productController.deleteProduct(1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(false);

        // Act
        Response response = productController.deleteProduct(1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
