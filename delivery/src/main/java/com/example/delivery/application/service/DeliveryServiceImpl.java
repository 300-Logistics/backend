package com.example.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.dto.DirectionsResponseDto;
import com.example.delivery.application.dto.HubPathResponse;
import com.example.delivery.application.service.interfaces.AssignDeliveryStaffService;
import com.example.delivery.application.service.interfaces.DeliveryService;
import com.example.delivery.domain.model.entity.CompanyDeliveryHistory;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.domain.model.entity.HubDeliveryHistory;
import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DeliveryStatusRecord;
import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.domain.model.vo.HubRoute;
import com.example.delivery.domain.repository.DeliveryRepository;
import com.example.delivery.infrastructure.client.HubClient;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final HubClient hubClient;
	private final AssignDeliveryStaffService assignDeliveryStaffService;
	private final NaverApiFacadeService naverApiFacadeService;

	@Override
	public CreateDeliveryResponseDto createDelivery(CreateDeliveryRequestDto requestDto, UUID userId, String userRole, String token) {
		// 1. 허브 정보 조회 후 허브 배송 경로 엔티티 생성
		String destinationHubAddress = hubClient.getHub(requestDto.destinationHubId(), token).getBody().address();

		HubPathResponse hubPathResponse = hubClient.searchHubPath(
			requestDto.startHubId(),
			requestDto.destinationHubId(),
			token).getBody();
		Integer hubDeliveryDuration = hubPathResponse.duration();
		Double hubDeliveryDistance = hubPathResponse.distance();

		HubRoute hubRoute = HubRoute.of(requestDto.startHubId(), requestDto.destinationHubId());
		DistanceAndDuration expectedHubDistanceAndDuration = DistanceAndDuration.of(hubDeliveryDistance, hubDeliveryDuration);

		validateParam(expectedHubDistanceAndDuration);

		HubDeliveryHistory hubDeliveryHistory = HubDeliveryHistory.of(
			hubRoute,
			expectedHubDistanceAndDuration,
			null
		);

		// 2. 허브 배송 담당자, 업체 배송 담당자 배정
		UUID hubDeliveryStaffId = assignDeliveryStaffService.assignHubDeliveryStaff();
		UUID companyDeliveryStaffId = assignDeliveryStaffService.assignCompanyDeliveryStaff(requestDto.destinationHubId());

		// 3. 업체 배송 경로 생성 후 업체 배송 경로 엔티티 생성
		DirectionsResponseDto naverResponseDto = naverApiFacadeService.getTrafastRoute(
			destinationHubAddress, requestDto.address(), null
		);

		double companyLongitude = naverResponseDto.route().routeSegmentList().get(0).path().get(0).longitude();
		double companyLatitude = naverResponseDto.route().routeSegmentList().get(0).path().get(0).latitude();

		DistanceAndDuration expectedCompanyDistanceAndDuration = DistanceAndDuration.of(
			naverResponseDto.route().distance(),
			naverResponseDto.route().duration());

		validateParam(hubDeliveryStaffId, companyDeliveryStaffId, requestDto.receiverId(), requestDto.receiverSlackId(), requestDto.orderId());

		CompanyDeliveryHistory companyDeliveryHistory = CompanyDeliveryHistory.of(
			Address.of(requestDto.address(), companyLongitude, companyLatitude),
			expectedCompanyDistanceAndDuration,
			null,
			companyDeliveryStaffId
		);

		// 4. 배송 엔티티 생성
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
			hubDeliveryHistory.getHubDeliveryHistoryId(),
			companyDeliveryHistory
		);

		deliveryRepository.save(delivery);

		return new CreateDeliveryResponseDto(
			delivery.getId(),
			delivery.getHubDeliveryStaffId(),
			delivery.getCompanyDeliveryStaffId(),
			delivery.getStartHubId(),
			delivery.getDestinationHubId(),
			destinationHubAddress,
			delivery.getReceiverId(),
			delivery.getReceiverSlackId(),
			delivery.getAddress().getFullAddress(),
			delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
			delivery.getDeliveryStatusRecord().getUpdatedAt(),
			hubDeliveryDistance,
			hubDeliveryDuration
		);
	}

	@Override
	public Delivery updateDeliveryStatus(UUID deliveryId, UUID userId, String userRole) {
		/**
		 *  권한 검증 해야지   마스터, 허브배송, 업체배송담당자만 가능
		 */
		Delivery delivery = getDelivery(deliveryId);

		delivery.updateDeliveryStatus();

		return deliveryRepository.save(delivery);
	}

	@Override
	public void cancelDelivery(UUID deliveryId, UUID userId, String userRole) {
		if (!"MASTER".equals(userRole)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		Delivery delivery = getDelivery(deliveryId);

		String username = "username";

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

	private static void validateParam(DistanceAndDuration expectedDistanceAndDuration) {
		if (expectedDistanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
	}
}
