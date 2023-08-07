package com.wanted.backend.service;

import com.wanted.backend.dto.PageResource;
import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.PostResponse;
import com.wanted.backend.entity.Post;

import java.nio.file.attribute.UserPrincipal;

public interface PostService {
    PageResource<PostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    Post createPost(CreatePostRequestDto createPostRequestDto, UserPrincipal currentUser);
    PostResponse findByPost(Long postId, UserPrincipal currentUser);
    Post updatePost(UpdatePostRequestDto updatePostRequestDto, UserPrincipal currentUser);
    void deletePost(Long postId, UserPrincipal currentUser);
}
