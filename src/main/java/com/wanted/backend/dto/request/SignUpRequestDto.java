package com.wanted.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequestDto {
    @Email(regexp = "^(?!.*@).*$", message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @NotBlank
    private String password;
    private String name;

}
