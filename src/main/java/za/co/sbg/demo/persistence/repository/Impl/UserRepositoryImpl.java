package za.co.sbg.demo.persistence.repository.Impl;

import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.persistence.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findByUsername(String name, String password) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password =:password", User.class)
                    .setParameter("name", name)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public User getUser(Long userId) {
        return em.find(User.class, userId);
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery("select p from User p", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}
