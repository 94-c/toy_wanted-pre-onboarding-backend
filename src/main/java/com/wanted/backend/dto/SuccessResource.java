package com.wanted.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResource<T> {

    private String message;
    private T data;

    public static <T> SuccessResource success(T data) {

        return SuccessResource.builder()
                .message("success")
                .data(data)
                .build();
    }
}
