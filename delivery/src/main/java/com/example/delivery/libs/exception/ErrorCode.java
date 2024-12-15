package com.example.delivery.libs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/*  400 BAD_REQUEST : 잘못된 요청  */
	INVALID_DISTANCE_NOT_BELOW_ZERO(400, "유효하지않은 거리입니다.  거리는 마이너스가 될 수 없습니다."),
	INVALID_DURATION_NOT_BELOW_ZERO(400, "유효하지않은 시간입니다. 시간은 마이너스가 될 수 없습니다."),
	INVALID_SEQUENCE_NOT_BELOW_ZERO(400, "유효하지않은 배송순서입니다. 순서는 마이너스가 될 수 없습니다."),
	INVALID_ADDRESS_EMPTY_OR_NULL(400, "유효하지않은 주소입니다. 공백 또는 NULL이 될 수 없습니다."),
	INVALID_HUB_DELIVERY_STAFF_EMPTY_OR_NULL(400, "유효하지않은 허브배송담당자입니다. 공백 또는 NULL이 될 수 없습니다."),
	INVALID_COMPANY_DELIVERY_STAFF_EMPTY_OR_NULL(400, "유효하지않은 업체배송담당자입니다. 공백 또는 NULL이 될 수 없습니다."),
	INVALID_RECEIVER_ID_NULL(400, "유효하지않은 수령인 ID입니다. NULL이 될 수 없습니다."),
	INVALID_RECEIVER_SLACK_ID_NULL(400, "유효하지않은 수령인 슬랙 ID입니다. NULL이 될 수 없습니다."),
	INVALID_ORDER_ID_NULL(400, "유효하지않은 주문 ID입니다. NULL이 될 수 없습니다."),
	INVALID_DELIVERY_STATUS_NULL(400, "유효하지않은 배송상태입니다. NULL이 될 수 없습니다."),
	INVALID_HUB_ID_NULL(400, "유효하지않은 허브 ID입니다. 허브 ID는 NULL이 될 수 없습니다."),
	INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL(400, "유효하지않은 거리 또는 시간입니다. NULL이 될 수 없습니다."),
	INVALID_HUB_ID_EQUALS(400, "유효하지않은 허브 ID입니다. 출발지 허브 ID와 도착지 허브 ID는 같을 수 없습니다."),
	INVALID_DELIVERY_STATUS(400, "유효하지않은 배송상태입니다. 허브에 도착 했거나, 배송 시작 전 이어야 합니다."),
	INVALID_DELIVERY_STATUS_CHANGE(400, "유효하지않은 배송상태입니다."),
	INVALID_LATITUDE(400, "유효하지않은 위도입니다. 대한민국의 위도는 북쪽 약 38.5  남쪽 약 33.0의 위도 내에 위치해야 합니다."),
	INVALID_LONGITUDE(400, "유효하지않은 경도입니다. 대한민국의 경도는 서쪽 약 124.0  동쪽 약 132.0의 위도 내에 위치해야 합니다."),
	ALREADY_COMPLETED_DELIVERY(400, "이미 배송완료상태입니다.  상태를 변경할 수 없습니다."),
	INVALID_PAGE_NUMBER_NOT_BELOW_ZERO(400, "유효하지않은 페이지 수입니다. 음수가 될 수 없습니다."),
	INVALID_PAGE_SIZE(400, "유효하지않은 페이지 사이즈입니다. 1 이상이어야합니다."),

	/*  401 UNAUTHORIZED : 인증 안됨  */
	UNAUTHORIZED(401, "인증되지 않았습니다."),

	/*  403 FORBIDDEN : 권한 없음  */
	FORBIDDEN(403, "권한이 없습니다."),

	/*  404 NOT_FOUND : Resource 권한 없음, Resource 를 찾을 수 없음  */
	ACCESS_DENIED(404, "접근 권한이 없습니다."),
	ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
	HUB_DELIVERY_STAFF_NOT_FOUND(404, "허브 배송담당자를 찾을 수 없습니다."),
	COMPANY_DELIVERY_STAFF_NOT_FOUND(404, "업체 배송담당자를 찾을 수 없습니다."),
	DELIVERY_NOT_FOUND(404, "해당 배송을 찾을 수 없습니다."),

	/*  408 REQUEST_TIMEOUT : 요청에 대한 응답 시간 초과  */
	TIMEOUT_ERROR(408, "응답시간을 초과하였습니다."),

	/*  409 CONFLICT : Resource 중복  */
	ALREADY_EXIST_USERID(409, "이미 존재하는 USERID 입니다."),

	/*  500 INTERNAL_SERVER_ERROR : 서버 에러  */
	INTERNAL_SERVER_ERROR(500, "내부 서버 에러입니다."),
	ORDER_SERVER_ERROR(500, "PRODUCT 서버 에러입니다."),
	INTERRUPTED_ERROR(500, " Interrupted 에러 발생."),

	/*  502 BAD_GATEWAY  연결 실패   */;

	private final Integer httpStatus;
	private final String message;
}