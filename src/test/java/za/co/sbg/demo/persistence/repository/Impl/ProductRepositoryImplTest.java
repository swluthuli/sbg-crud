package za.co.sbg.demo.persistence.repository.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.persistence.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Product> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        // Arrange
        Product product = Product.builder().name("New Product").build();

        // Act
        productRepository.addProduct(product);

        // Assert
        verify(entityManager, times(1)).persist(product);
    }

    @Test
    void testGetProduct() {
        // Arrange
        Product product =  Product.builder().id(1L).build();

        when(entityManager.find(Product.class, 1L)).thenReturn(product);

        // Act
        Product result = productRepository.getProduct(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(entityManager, times(1)).find(Product.class, 1L);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProductList = Arrays.asList(new Product(1L, "Product1",12), new Product(2L, "Product2",21));
        when(entityManager.createQuery("select p from Product p", Product.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockProductList);

        // Act
        List<Product> result = productRepository.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery("select p from Product p", Product.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product = Product.builder().id(1L).name("updated Product").build();

        // Act
        productRepository.updateProduct(product);

        // Assert
        verify(entityManager, times(1)).merge(product);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Product product = Product.builder().id(1L).build();

        when(entityManager.contains(product)).thenReturn(true);

        // Act
        productRepository.deleteProduct(product);

        // Assert
        verify(entityManager, times(1)).remove(product);
    }

    @Test
    void testDeleteProduct_NotContained() {
        // Arrange
        Product product =  Product.builder().id(1L).build();

        when(entityManager.contains(product)).thenReturn(false);
        when(entityManager.merge(product)).thenReturn(product);

        // Act
        productRepository.deleteProduct(product);

        // Assert
        verify(entityManager, times(1)).merge(product);
        verify(entityManager, times(1)).remove(product);
    }
}
