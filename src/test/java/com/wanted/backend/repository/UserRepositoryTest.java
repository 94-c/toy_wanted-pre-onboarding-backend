package com.wanted.backend.repository;

import com.wanted.backend.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("JPA 연결 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @DisplayName("SELECT 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertThat(users)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("INSERT 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = userRepository.count();
        User user = User.of("hyeongwoo26@gmail.com", "1234", "최형우");

        // When
        userRepository.save(user);

        // Then
        assertThat(userRepository.count()).isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("UPDATE 테스트")
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        String updatedName = "Updated Name";
        user.setName(updatedName);

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId())
                .isNotNull()
                .isEqualTo(1L);
        assertThat(savedUser.getName())
                .isEqualTo(updatedName);
    }

    @Test
    @DisplayName("DELETE 테스트")
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        User user = userRepository.findById(1L).orElseThrow();

        // When
        userRepository.delete(user);
        userRepository.flush();

        // Then
        assertFalse(userRepository.existsById(1L));
    }
}