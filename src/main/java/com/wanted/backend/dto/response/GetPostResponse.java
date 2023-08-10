package com.wanted.backend.dto.response;

import com.wanted.backend.entity.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetPostResponse {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserResponse users;

    public static GetPostResponse convertToPostResponse(Post post) {
        return GetPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .users(UserResponse.convertToUserResponse(post.getUser()))
                .build();
    }
}