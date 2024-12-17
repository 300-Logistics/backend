package com.example.slack.libs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/*  400 BAD_REQUEST : 잘못된 요청  */
	EXPIRED_TOKEN(400, "만료된 토큰입니다."),
	INVALID_TOKEN(400, "유효하지않은 토큰입니다."),
	INVALID_USERNAME(400, "유저네임은 필수입니다."),
	INVALID_PASSWORD(400, "비밀번호는 필수입니다."),
	INVALID_USERNAME_LENGTH(400, "3자 이상 15자 이하만 가능합니다."),
	INVALID_PASSWORD_LENGTH(400, "8자 이상 15자 이하만 가능합니다."),
	INVALID_USERNAME_TYPE(400, "유저네임은 소문자 영어 또는 숫자만 가능합니다."),
	INVALID_PASSWORD_TYPE(400, "비밀번호는 소문자 영어 또는 숫자만 가능합니다."),
	PASSWORD_DOES_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
	ALREADY_REGISTERED_DELIVERY_STAFF(400, "이미 등록된 배송 담당자입니다."),
	INVALID_PARAMETER(400, "파라미터 값이 제공되어야합니다."),
	NO_ROOM_FOR_HUB_DELIVERY_STAFF(400, "허브배송담당자를 더이상 추가할 수 없습니다."),
	NO_ROOM_FOR_COMPANY_DELIVERY_STAFF(400, "업체배송담당자를 더이상 추가할 수 없습니다."),

	/*  401 UNAUTHORIZED : 인증 안됨  */
	UNAUTHORIZED(401, "인증되지 않았습니다."),

	/*  403 FORBIDDEN : 권한 없음  */
	FORBIDDEN(403, "권한이 없습니다."),

	/*  404 NOT_FOUND : Resource 권한 없음, Resource 를 찾을 수 없음  */
	ACCESS_DENIED(404, "접근 권한이 없습니다."),
	USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
	DELIVERY_STAFF_NOT_FOUND(404, "배송담당자를 찾을 수 없습니다."),

	/*  408 REQUEST_TIMEOUT : 요청에 대한 응답 시간 초과  */
	TIMEOUT_ERROR(408, "응답시간을 초과하였습니다."),

	/*  409 CONFLICT : Resource 중복  */
	ALREADY_EXIST_USERID(409, "이미 존재하는 USERID 입니다."),
	ALREADY_EXIST_NICKNAME(409, "이미 존재하는 NICKNAME 입니다."),

	/*  500 INTERNAL_SERVER_ERROR : 서버 에러  */
	INTERNAL_SERVER_ERROR(500, "내부 서버 에러입니다."),
	INTERRUPTED_ERROR(500, " Interrupted 에러 발생."),

	/*  502 BAD_GATEWAY  연결 실패   */
	;

	private final Integer httpStatus;
	private final String message;
}
