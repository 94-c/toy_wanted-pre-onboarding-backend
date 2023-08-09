package com.wanted.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePostRequestDto {
    private String title;
    private String content;
}
