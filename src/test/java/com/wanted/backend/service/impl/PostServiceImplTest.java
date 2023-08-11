package com.wanted.backend.service.impl;

import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.GetPostResponse;
import com.wanted.backend.entity.Post;
import com.wanted.backend.entity.User;
import com.wanted.backend.repository.AuthRepository;
import com.wanted.backend.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthRepository authRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_createPost() {
        // Given
        CreatePostRequestDto requestDto = CreatePostRequestDto.builder()
                .title("테스트 컨텐츠")
                .content("신규 컨텐츠")
                .build();
        Principal currentUser = mock(Principal.class);
        when(currentUser.getName()).thenReturn("test@example.com");

        User user = User.of("hyeongwoo26@gmail.com", "1234", "테스");

        when(authRepository.findByEmail(currentUser.getName())).thenReturn(Optional.of(user));

        Post createdPost = new Post(1L, "New Post", "Content", user);
        when(postRepository.save(any(Post.class))).thenReturn(createdPost);

        // When
        Post result = postService.createPost(requestDto, currentUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("New Post");
    }

    @Test
    void test_findByPost() {
        // Given
        Long postId = 1L;
        Post post = new Post(postId, "Title", "Content", User.of("hyeongwoo26@gmail.com", "1234", "테스"));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        GetPostResponse result = postService.findByPost(postId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Title");

    }

    @Test
    void test_updatePost() {
        // Given
        Long postId = 1L;
        UpdatePostRequestDto requestDto = UpdatePostRequestDto.builder()
                .title("테스트 수정")
                .content("테스트 수정 컨텐츠")
                .build();
        Principal currentUser = mock(Principal.class);
        when(currentUser.getName()).thenReturn("test@example.com");

        Post existingPost = new Post(postId, "Title", "Content",  User.of("hyeongwoo26@gmail.com", "1234", "테스"));
        when(postRepository.findByIdAndUserEmail(postId, currentUser.getName())).thenReturn(Optional.of(existingPost));

        Post updatedPost = new Post(postId, "Updated Title", "Updated Content", User.of("hyeongwoo26@gmail.com", "1234", "테스"));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // When
        Post result = postService.updatePost(postId, requestDto, currentUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Title");
    }



}