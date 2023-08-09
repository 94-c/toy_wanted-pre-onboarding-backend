package com.wanted.backend.repository;

import com.wanted.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "test@example.com";

        User user = new User(email, null, null, null);

        entityManager.persist(user);
        entityManager.flush();

        // Act
        Optional<User> foundUser = authRepository.findByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }
}