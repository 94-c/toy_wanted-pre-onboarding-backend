package com.wanted.backend.repository;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("회원 가입")
    void givenSignUpRequest_whenSavingUser_thenSavesUser() {
        // Given
        final SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        // When
        final User user = User.of(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName());
        final User joinUser = authRepository.save(user);

        // Then
        assertThat(joinUser)
                .isEqualTo(user)
                .extracting(User::getEmail)
                .isEqualTo(signUpRequestDto.getEmail());
        assertThat(joinUser.getId()).isNotNull();
    }


    @Test
    @DisplayName("이메일 회원 조회")
    void givenSignUpRequest_whenFindByEmail_then() {
        // Given
        final SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("hyeongwoo26@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        // When
        final User user = User.of(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName());
        final User joinUser = authRepository.save(user);

        // Then
        final Optional<User> result = authRepository.findByEmail(joinUser.getEmail());
        final User findByUser = result.orElseThrow(() -> new IllegalArgumentException("이메일을 찾지 못했습니다 :: " + joinUser.getEmail()));

        assertThat(findByUser.getEmail()).isEqualTo("hyeongwoo26@gmail.com");
    }

}