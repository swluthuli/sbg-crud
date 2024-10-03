package za.co.sbg.demo.persistence.repository.Impl;

import javax.transaction.Transactional;
import za.co.sbg.demo.persistence.entity.Product;
import za.co.sbg.demo.persistence.repository.ProductRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@RequestScoped
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void addProduct(Product product) {
        em.persist(product);
    }

    @Override
    public Product getProduct(Long productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public List<Product> getAllProducts() {
        TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        em.merge(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        em.remove(em.contains(product) ? product : em.merge(product));
    }
}
