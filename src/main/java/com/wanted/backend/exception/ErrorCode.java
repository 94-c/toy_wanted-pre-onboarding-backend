package com.wanted.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED("이메일이 중복되었습니다"),
    WRONG_PASSWORD_INFO("패스워드 길이를 8자 이상 작성해 주세요"),
    LOGIN_INFO_INCORRECT("아이디 혹은 비밀번호가 잘못되었습니다"),
    MEMBER_NOT_FOUND("존재하지 않는 사용자입니다"),
    POST_NOT_FOUND("존재하지 않는 게시물입니다"),
    UNAUTHORIZED("해당 자원에 접근가능한 권한이 없습니다"),
    UNAUTHENTICATED("인증되지 않은 사용자입니다");

    private final String message;


}
