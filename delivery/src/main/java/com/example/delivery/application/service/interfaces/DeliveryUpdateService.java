// package com.example.delivery.application.service.interfaces;
//
// import java.util.UUID;
//
// import com.example.delivery.application.dto.UpdateDeliveryResponseDto;
// import com.example.delivery.presentation.dto.UpdateDeliveryRequestDto;
//
// public interface DeliveryUpdateService {
//
// 	UpdateDeliveryResponseDto updateDelivery(UUID deliveryId, UpdateDeliveryRequestDto requestDto, UUID userId, String userRole);
//
// }
/**
 * 주소 변경을 배송에서 처리할 이유가 없음.
 * 배송 시작 전에 주문에서 모두 처리 해야 안전.
 * 코드는 참고용으로 남겨둠
 */

// @Override
// public UpdateDeliveryResponseDto updateDelivery(UUID deliveryId, UpdateDeliveryRequestDto requestDto, UUID userId, String userRole) {
// 	/**
// 	 * authClient에서 유저 ID로 권한확인.  마스터인지
// 	 * order의 주문자 ID와 파라미터로 받은  유저 ID가 동일한지 확인해서 주문자 본인인지 확인
// 	 *
// 	 * 이 둘인 경우에만 진행  그 외엔 401
// 	 */
//
// 	Delivery delivery = getDelivery(deliveryId);
//
// 	// 바뀐 주소 적용. 업체 배송 경로 다시 가져옴
// 	String destinationHubAddress = hubClient.getHub(delivery.getDestinationHubId()).getBody().address();
//
// 	DirectionsResponseDto naverResponseDto = naverApiFacadeService.getTrafastRoute(
// 		destinationHubAddress, requestDto.address(), null
// 	);
//
// 	if (naverResponseDto.route() == null || naverResponseDto.route().routeSegmentList().isEmpty()) {
// 		throw new CustomException(ErrorCode.INVALID_NAVER_ROUTE_RESPONSE);
// 	}
//
// 	double companyLongitude = naverResponseDto.route().routeSegmentList().get(0).path().get(0).longitude();
// 	double companyLatitude = naverResponseDto.route().routeSegmentList().get(0).path().get(0).latitude();
//
// 	DistanceAndDuration expectedCompanyDistanceAndDuration = DistanceAndDuration.of(
// 		naverResponseDto.route().distance(),
// 		naverResponseDto.route().duration());
//
// 	Address updatedAddress = Address.of(requestDto.address(), companyLatitude, companyLongitude);
//
// 	// 배송 담당자 새로 배정
// 	UUID companyDeliveryStaffId = assignDeliveryStaffService.assignCompanyDeliveryStaff(delivery.getDestinationHubId());
//
// 	validateParamInCompanyDeliveryHistory(expectedCompanyDistanceAndDuration, companyDeliveryStaffId);
//
// 	// 업체 배송 경로 기록 엔티티 새로 생성
// 	CompanyDeliveryHistory updatedHistory = CompanyDeliveryHistory.of(
// 		updatedAddress, expectedCompanyDistanceAndDuration, null, companyDeliveryStaffId);
//
// 	// 수정 가능한 배송 상태인지 확인 및 배송 엔티티에 경로 업데이트
// 	checkDeliveryStatus(delivery.getDeliveryStatusRecord().getDeliveryStatus());
// 	delivery.update(updatedAddress, null, companyDeliveryStaffId);
// 	delivery.setCompanyDeliveryHistory(updatedHistory);
//
// 	return new UpdateDeliveryResponseDto(
// 		delivery.getId(),
// 		delivery.getAddress().getFullAddress(),
// 		delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
// 		LocalDateTime.now());
// }