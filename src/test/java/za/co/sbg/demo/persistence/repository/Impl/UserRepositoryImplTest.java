package za.co.sbg.demo.persistence.repository.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<User> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername() {
        User user = User.builder()
                .name("test")
                .password("password")
                .build();

        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        User result = userRepository.findByUsername("test", "password");

        assertNotNull(result);
        assertEquals("test", result.getName());
        verify(entityManager, times(1)).createQuery(anyString(), eq(User.class));
    }

    @Test
    void testCreateUser() {
        User user = User.builder()
                .name("test")
                .password("password")
                .build();

        userRepository.createUser(user);

        verify(entityManager, times(1)).persist(user);
    }

    @Test
    void testGetUser() {
        User user = User.builder()
                .id(1L)
                .name("test")
                .build();

        when(entityManager.find(User.class, 1L)).thenReturn(user);

        User result = userRepository.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(entityManager, times(1)).find(User.class, 1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUserList = Arrays.asList(
                User.builder().id(1L).name("User1").build(),
                User.builder().id(2L).name("User2").build()
        );

        when(entityManager.createQuery("select p from User p", User.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockUserList);

        List<User> result = userRepository.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery("select p from User p", User.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdateUser() {
        User user = User.builder()
                .id(1L)
                .name("Updated User")
                .build();

        userRepository.updateUser(user);

        verify(entityManager, times(1)).merge(user);
    }

    @Test
    void testDeleteUser() {
        User user = User.builder()
                .id(1L)
                .build();

        when(entityManager.contains(user)).thenReturn(true);

        userRepository.deleteUser(user);

        verify(entityManager, times(1)).remove(user);
    }

    @Test
    void testDeleteUser_NotContained() {
        User user = User.builder()
                .id(1L)
                .build();

        when(entityManager.contains(user)).thenReturn(false);
        when(entityManager.merge(user)).thenReturn(user);

        userRepository.deleteUser(user);

        verify(entityManager, times(1)).merge(user);
        verify(entityManager, times(1)).remove(user);
    }
}
