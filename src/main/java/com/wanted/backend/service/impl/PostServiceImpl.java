package com.wanted.backend.service.impl;

import com.wanted.backend.dto.PageResource;
import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.GetPostResponse;
import com.wanted.backend.dto.response.UserResponse;
import com.wanted.backend.entity.Post;
import com.wanted.backend.entity.User;
import com.wanted.backend.exception.ErrorCode;
import com.wanted.backend.exception.GlobalException;
import com.wanted.backend.repository.AuthRepository;
import com.wanted.backend.repository.PostRepository;
import com.wanted.backend.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AuthRepository authRepository;

    public PostServiceImpl(PostRepository postRepository, AuthRepository authRepository) {
        this.postRepository = postRepository;
        this.authRepository = authRepository;
    }

    @Override
    public PageResource<GetPostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<GetPostResponse> content = listOfPosts.stream().map(GetPostResponse::convertToPostResponse).collect(Collectors.toList());

        PageResource<GetPostResponse> pageResource = new PageResource<>();

        pageResource.setContent(content);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(posts.getTotalElements());
        pageResource.setTotalPages(posts.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public Post createPost(CreatePostRequestDto createPostRequestDto, Principal currentUser) {

        Optional<User> findByUser = authRepository.findByEmail(currentUser.getName());

        User findUser = findByUser.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        Post createPost = Post.of(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), findUser);
        createPost.setCreatedAt(LocalDateTime.now());

        return postRepository.save(createPost);
    }

    @Override
    public GetPostResponse findByPost(Long postId) {
        Optional<Post> findByPostId = postRepository.findById(postId);

        Post findByPost = findByPostId.orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        return GetPostResponse.builder()
                .id(findByPost.getId())
                .title(findByPost.getTitle())
                .content(findByPost.getContent())
                .createdAt(findByPost.getCreatedAt())
                .modifiedAt(findByPost.getModifiedAt())
                .users(UserResponse.convertToUserResponse(findByPost.getUser()))
                .build();
    }

    @Override
    public Post updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Principal currentUser) {

        Optional<Post> findByPostId = postRepository.findByIdAndUserEmail(postId, currentUser.getName());

        Post findByPost = findByPostId.orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        findByPost.setTitle(updatePostRequestDto.getTitle());
        findByPost.setContent(updatePostRequestDto.getContent());
        findByPost.setModifiedAt(LocalDateTime.now());

        return postRepository.save(findByPost);
    }

    @Override
    public void deletePost(Long postId, Principal currentUser) {
        Optional<Post> findByPostId = postRepository.findByIdAndUserEmail(postId, currentUser.getName());

        Post findByPost = findByPostId.orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        postRepository.delete(findByPost);
    }
}
