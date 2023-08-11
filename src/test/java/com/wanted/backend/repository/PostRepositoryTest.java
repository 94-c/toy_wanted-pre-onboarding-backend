package com.wanted.backend.repository;

import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.entity.Post;
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
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("게시물 등록")
    void givenCreatePostRequestDto_whenSavingPosts_thenSavesPosts() {
        // Given
        final SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("hyeongwoo26@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        final CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .title("테스트 게시글")
                .content("테스트 게시글 컨텐트")
                .build();

        // When
        final User user = User.of(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName());
        final User joinUser = authRepository.save(user);

        final Post post = Post.of(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), joinUser);
        final Post savePost = postRepository.save(post);

        // Then
        assertThat(savePost)
                .isEqualTo(post)
                .extracting(Post::getTitle)
                .isEqualTo(createPostRequestDto.getTitle());
        assertThat(savePost.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시물 찾기")
    void givenFindPostId_whenFindPosts_thenReturnPosts() {
        // Given

        // When
        final Optional<Post> findByPost = postRepository.findById(1L);

        // Then
        assertThat(findByPost)
                .isPresent()
                .get()
                .isEqualTo(findByPost);
    }

    @Test
    @DisplayName("게시글 저장 및 조회")
    void test_save_and_find_post() {
        // Given
        final SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("hyeongwoo26@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        final CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .title("테스트 게시글")
                .content("테스트 게시글 내용")
                .build();

        // When
        final User user = User.of(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName());
        final User joinUser = authRepository.save(user);

        Post post = Post.of(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), joinUser);
        final Post savedPost = postRepository.save(post);
        final Optional<Post> findByPost = postRepository.findById(savedPost.getId());

        // Then
        assertThat(savedPost)
                .isEqualTo(post)
                .extracting(Post::getTitle)
                .isEqualTo(createPostRequestDto.getTitle());
        assertThat(savedPost.getId()).isNotNull();

        assertThat(findByPost)
                .isPresent()
                .get()
                .isEqualTo(savedPost);
    }


}