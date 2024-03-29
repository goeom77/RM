package org.gyu.solution.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "C001", "해당 자원이 존재하지 않습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "C002", "이미 존재하는 데이터입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C005", "잘못된 요청입니다."),
    INVALID_TYPE_VALUE(HttpStatus.NOT_FOUND, "C006", " Invalid Type Value"),


    /*로그인*/
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "L001", "로그인이 필요합니다."),
    FAIL_LOGIN(HttpStatus.BAD_REQUEST, "L002", "잘못된 아이디 또는 비밀번호입니다."),

    /*유저*/
    DUPLICATE_USER(HttpStatus.FORBIDDEN, "U001", "존재하는 아이디입니다."),
    /* encrypt error*/
    ENCRYPT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "암호화 에러"),
    /*서비스*/
    OVER_LIMIT_USER(HttpStatus.FORBIDDEN, "S001", "사용자 수를 초과하였습니다."),
    MANAGER_DELETE_REQUEST(HttpStatus.FORBIDDEN, "S002", "관리자를 삭제 요청하고 있습니다."),
    /*토큰 에러*/
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "T001", "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
