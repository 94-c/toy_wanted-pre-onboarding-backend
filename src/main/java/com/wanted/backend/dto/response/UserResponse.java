package com.wanted.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.backend.entity.Post;
import com.wanted.backend.entity.User;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    public static UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }

}
