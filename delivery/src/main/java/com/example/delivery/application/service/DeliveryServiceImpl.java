package com.example.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DeliveryStatusRecord;
import com.example.delivery.domain.repository.DeliveryRepository;
import com.example.delivery.infrastructure.client.HubClient;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService{

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

		Delivery delivery = Delivery.of(
			DeliveryStatusRecord.of(DeliveryStatus.WAITING_IN_START_HUB),
			Address.of(requestDto.address(), 0, 0),
			requestDto.startHubId(),
			requestDto.destinationHubId(),
			requestDto.receiverId(),
			requestDto.receiverSlackId(),
			requestDto.orderId(),
			hubDeliveryStaffId,
			companyDeliveryStaffId,
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
}
