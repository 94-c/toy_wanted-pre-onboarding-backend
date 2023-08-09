package com.wanted.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.backend.dto.CustomErrorResource;
import com.wanted.backend.dto.ErrorResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //필요한 권한이 없어 접근하려 할때 403 에러
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try(ServletOutputStream outputStream = response.getOutputStream()) {
            ErrorResource errorResource = ErrorResource.builder()
                    .message("forbidden")
                    .build();

            errorResource.addError(CustomErrorResource.builder()
                    .message("토큰이 유효하지 않습니다. 다시 로그인을 해주세요.")
                    .build());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, errorResource);
            outputStream.flush();
        }
    }
}
