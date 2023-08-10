package com.wanted.backend.controller;

import com.wanted.backend.dto.PageResource;
import com.wanted.backend.dto.SuccessResource;
import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.CreatePostResponse;
import com.wanted.backend.dto.response.GetPostResponse;
import com.wanted.backend.dto.response.UpdatePostResponse;
import com.wanted.backend.dto.response.UserResponse;
import com.wanted.backend.entity.Post;
import com.wanted.backend.service.PostService;
import com.wanted.backend.util.PagingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResource getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = PagingUtil.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PagingUtil.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PagingUtil.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PagingUtil.DEFAULT_SORT_DIREACTION, required = false) String sortDir) {

        PageResource findAllPosts = postService.findAllPosts(pageNo, pageSize, sortBy, sortDir);

        return SuccessResource.success(PageResource.builder()
                .content(findAllPosts.getContent())
                .pageNo(findAllPosts.getPageNo())
                .pageSize(findAllPosts.getPageSize())
                .totalElements(findAllPosts.getTotalElements())
                .totalPages(findAllPosts.getTotalPages())
                .last(findAllPosts.isLast())
                .build());
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResource<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequestDto postDTO,
                                                          Principal currentUser) {

        Post createPost = postService.createPost(postDTO, currentUser);

        return SuccessResource.success(CreatePostResponse.builder()
                .title(createPost.getTitle())
                .content(createPost.getContent())
                .users(UserResponse.convertToUserResponse(createPost.getUser()))
                .createdAt(createPost.getCreatedAt())
                .build());
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResource getPost(@PathVariable(name = "id") Long id) {

        GetPostResponse post = postService.findByPost(id);

        return SuccessResource.success(GetPostResponse.builder()
                .id(post.getId())
                .users(post.getUsers())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResource<UpdatePostResponse> updatePost(@Valid @RequestBody UpdatePostRequestDto postDTO,
                                                          @PathVariable(name = "id") Long postId,
                                                          Principal currentUser) {

        Post updatePost = postService.updatePost(postId, postDTO, currentUser);

        return SuccessResource.success(UpdatePostResponse.builder()
                .id(updatePost.getId())
                .title(updatePost.getTitle())
                .content(updatePost.getContent())
                .users(UserResponse.convertToUserResponse(updatePost.getUser()))
                .createdAt(updatePost.getCreatedAt())
                .modifiedAt(updatePost.getModifiedAt())
                .build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResource<String> deletePost(@PathVariable(name = "id") Long postId,
                                              Principal currentUser) {

        postService.deletePost(postId, currentUser);

        return SuccessResource.success("해당 게시글이 삭제 되었습니다.");
    }
}
