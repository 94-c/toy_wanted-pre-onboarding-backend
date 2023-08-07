package com.wanted.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    private String message;
    private T data;

    public static <T> SuccessResponse success(T data) {

        return SuccessResponse.builder()
                .message("success")
                .data(data)
                .build();
    }
}
