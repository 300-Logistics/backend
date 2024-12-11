package com.example.hub.libs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*  400 BAD_REQUEST : 잘못된 요청  */
    INVALID_DISTANCE_NOT_BELOW_ZERO(400, "유효하지않은 거리입니다.  거리는 마이너스가 될 수 없습니다."),
    INVALID_DURATION_NOT_BELOW_ZERO(400, "유효하지않은 시간입니다. 시간은 마이너스가 될 수 없습니다."),
    INVALID_ADDRESS_EMPTY_OR_NULL(400, "유효하지않은 주소입니다. 공백 또는 NULL이 될 수 없습니다."),
    INVALID_HUB_ID_NULL(400, "유효하지않은 허브 ID입니다. 허브 ID는 NULL이 될 수 없습니다."),
    INVALID_HUB_ID_EQUALS(400, "유효하지않은 허브 ID입니다. 출발지 허브 ID와 도착지 허브 ID는 같을 수 없습니다."),

    /*  401 UNAUTHORIZED : 인증 안됨  */
    UNAUTHORIZED(401, "인증되지 않았습니다."),

    /*  403 FORBIDDEN : 권한 없음  */
    FORBIDDEN(403, "권한이 없습니다."),

    /*  404 NOT_FOUND : Resource 권한 없음, Resource 를 찾을 수 없음  */
    ACCESS_DENIED(404, "접근 권한이 없습니다."),
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
    HUB_NOT_FOUND(404, "허브를 찾을 수 없습니다."),

    /*  408 REQUEST_TIMEOUT : 요청에 대한 응답 시간 초과  */
    TIMEOUT_ERROR(408, "응답시간을 초과하였습니다."),

    /*  409 CONFLICT : Resource 중복  */
    ALREADY_EXIST_USERID(409, "이미 존재하는 USERID 입니다."),

    /*  500 INTERNAL_SERVER_ERROR : 서버 에러  */
    INTERNAL_SERVER_ERROR(500, "내부 서버 에러입니다."),
    ORDER_SERVER_ERROR(500, "PRODUCT 서버 에러입니다."),
    INTERRUPTED_ERROR(500, " Interrupted 에러 발생."),

    /*  502 BAD_GATEWAY  연결 실패   */
    ;

    private final Integer httpStatus;
    private final String message;
}