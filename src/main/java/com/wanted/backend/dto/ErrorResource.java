package com.wanted.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public
class ErrorResource {
    private String message;
    @Builder.Default
    private List<CustomErrorResource> errors = new ArrayList<>();

    public List<CustomErrorResource> addError(CustomErrorResource error) {
        this.errors.add(error);
        return this.errors;
    }
}
