package com.example.delivery.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.dto.UpdateDeliveryResponseDto;
import com.example.delivery.application.service.interfaces.AssignDeliveryStaffService;
import com.example.delivery.application.service.interfaces.DeliveryService;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DeliveryStatusRecord;
import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.domain.repository.DeliveryRepository;
import com.example.delivery.infrastructure.client.HubClient;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;
import com.example.delivery.presentation.dto.UpdateDeliveryRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final HubClient hubClient;
	private final AssignDeliveryStaffService assignDeliveryStaffService;

	@Override
	public CreateDeliveryResponseDto createDelivery(CreateDeliveryRequestDto requestDto) {
		// TODO: 허브 경로기록을 파라미터를 startHubId와 destinationHubId를 사용해서 조회할 수 있는지.  -> 가능
		// TODO: 경로기록에서 실제 거리 실제 소요시간은 도착해보지않으면 모르는 기록인데 어떻게 컬럼을 관리할지  -> null 처리

		/**
		 * 여기 허브 배송 경로 기록  페인클라이언트 던지기
		 * HubDeliveryResponseDto HubRoute = hubClient.getHubRoute(requestDto.startHubId(), requestDto.destinationHubId());
		 * 업체배송경로기록  서비스에서 만들어서 가져오기
		 */

		UUID hubDeliveryStaffId = assignDeliveryStaffService.assignHubDeliveryStaff();
		UUID companyDeliveryStaffId = assignDeliveryStaffService.assignCompanyDeliveryStaff(requestDto.destinationHubId());

		validateParam(hubDeliveryStaffId, companyDeliveryStaffId, requestDto.receiverId(), requestDto.receiverSlackId(), requestDto.orderId());

		Delivery delivery = Delivery.of(
			DeliveryStatusRecord.of(DeliveryStatus.WAITING_IN_START_HUB),
			Address.of(requestDto.address(), 0, 0),
			requestDto.startHubId(),
			requestDto.destinationHubId(),
			hubDeliveryStaffId,
			companyDeliveryStaffId,
			requestDto.receiverId(),
			requestDto.receiverSlackId(),
			requestDto.orderId(),
			null,
			null
		);

		deliveryRepository.save(delivery);

		return new CreateDeliveryResponseDto(
			delivery.getId(),
			delivery.getHubDeliveryStaffId(),
			delivery.getCompanyDeliveryStaffId(),
			delivery.getStartHubId(),
			delivery.getDestinationHubId(),
			delivery.getReceiverId(),
			delivery.getReceiverSlackId(),
			delivery.getAddress().getFullAddress(),
			delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
			delivery.getDeliveryStatusRecord().getUpdatedAt(),
			// TODO: 아래 두개는 페인클라이언트로 허브배송경로기록을 가져와서 그 안에서 get으로 꺼내서 저장
			1.0,
			1
		);
	}

	@Override
	public UpdateDeliveryResponseDto updateDelivery(UUID deliveryId, UpdateDeliveryRequestDto requestDto) {
		/**
		 * authClient에서 유저 ID로 권한확인.  마스터인지
		 * order의 주문자 ID와 파라미터로 받은  유저 ID가 동일한지 확인해서 주문자 본인인지 확인
		 *
		 * 이 둘인 경우에만 진행  그 외엔 401
		 */

		Delivery delivery = getDelivery(deliveryId);

		/**
		 *  네이버 지도 API를 통해  address를 위경도로 변환하고 배송경로 생성
		 *  double distance = api호출
		 *  int duration = api 호출
		 *  DistanceAndDuration updatedDistanceAndDuration = DistanceAndDuration.of(distance, duration);
		 *
		 *  그리고 아래 위경도에 가져온 값을 넣으면 됨
		 */
		Address updatedAddress = Address.of(requestDto.address(), 37.571, 126.976);

		UUID companyDeliveryStaffId = assignDeliveryStaffService.assignCompanyDeliveryStaff(delivery.getDestinationHubId());
		/**
		 *   of 쓰기전 검증
		 *   validateParamInCompanyDeliveryHistory(updatedDistanceAndDuration, companyDeliveryStaffId);
 		 */

		/**
		 *  새 업체배송경로에 저장
		 *
		 *   CompanyDeliveryHistory updatedHistory = CompanyDeliveryHistory.of(
		 *   updatedAddress, updatedDistanceAndDuration, null, delivery.getCompanyDeliveryStaffId);
		 *
		 */


		checkDeliveryStatus(delivery.getDeliveryStatusRecord().getDeliveryStatus());
		delivery.update(updatedAddress, null, companyDeliveryStaffId);
		// delivery.setCompanyDeliveryHistory(updatedHistory);

		return new UpdateDeliveryResponseDto(
			delivery.getId(),
			delivery.getAddress().getFullAddress(),
			delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
			LocalDateTime.now());
	}

	// @Override
	// public UpdateDeliveryStatusResponseDto updateDeliveryStatus(UUID deliveryId) {
	// 	Delivery delivery = getDelivery(deliveryId);
	//
	// 	delivery.updateDeliveryStatus();
	//
	// 	deliveryRepository.save(delivery);
	//
	// 	return new UpdateDeliveryStatusResponseDto(
	// 		delivery.getId(),
	// 		delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
	// 		LocalDateTime.now());
	// }
	@Override
	public Delivery updateDeliveryStatus(UUID deliveryId) {
		Delivery delivery = getDelivery(deliveryId);

		delivery.updateDeliveryStatus();

		return deliveryRepository.save(delivery);
	}

	@Override
	public void cancelDelivery(UUID deliveryId) {
		Delivery delivery = getDelivery(deliveryId);

		String username = "빨리좀해라";

		delivery.cancelDelivery(username);

		deliveryRepository.save(delivery);
	}

	private Delivery getDelivery(UUID deliveryId) {
		return deliveryRepository.findById(deliveryId)
			.orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
	}

	private void validateParam(UUID hubDeliveryStaffId, UUID companyDeliveryStaffId, UUID receiverId, String receiverSlackId, UUID orderId) {
		if (hubDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_HUB_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
		if (companyDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_COMPANY_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
		if (receiverId == null) {
			throw new CustomException(ErrorCode.INVALID_RECEIVER_ID_NULL);
		}
		if (receiverSlackId == null) {
			throw new CustomException(ErrorCode.INVALID_RECEIVER_SLACK_ID_NULL);
		}
		if (orderId == null) {
			throw new CustomException(ErrorCode.INVALID_ORDER_ID_NULL);
		}
	}

	private static void validateParamInCompanyDeliveryHistory(DistanceAndDuration expectedDistanceAndDuration, UUID companyDeliveryStaffId) {
		if (expectedDistanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
		if (companyDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_COMPANY_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
	}

	private void checkDeliveryStatus(DeliveryStatus deliveryStatus) {
		if (deliveryStatus != DeliveryStatus.ARRIVED_AT_DESTINATION_HUB &&
			deliveryStatus != DeliveryStatus.BEFORE_DELIVERY_START) {
			throw new CustomException(ErrorCode.INVALID_DELIVERY_STATUS);
		}
	}
}
