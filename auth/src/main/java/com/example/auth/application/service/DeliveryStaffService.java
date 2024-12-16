package com.example.auth.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.domain.model.entity.DeliveryStaff;
import com.example.auth.domain.model.entity.User;
import com.example.auth.domain.model.enums.DeliveryType;
import com.example.auth.domain.model.vo.Username;
import com.example.auth.domain.repository.AuthRepository;
import com.example.auth.domain.repository.DeliveryStaffRepository;
import com.example.auth.libs.exception.CustomException;
import com.example.auth.libs.exception.ErrorCode;
import com.example.auth.presentation.dto.DeliveryStaffRegisterRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryStaffService {

	private final DeliveryStaffRepository deliveryStaffRepository;
	private final AuthRepository authRepository;

	@Transactional
	public void registerDeliveryStaff(DeliveryStaffRegisterRequestDto requestDto, String userRole) {
		checkUserRole(userRole);

		User user = getUser(requestDto);

		int hubDeliveryStaffCount = countHubDeliveryStaff();
		checkHubDeliveryStaffCount(hubDeliveryStaffCount);

		int companyDeliveryStaffCount = countCompanyDeliveryStaff(requestDto);
		checkCompanyDeliveryStaffCount(companyDeliveryStaffCount);

		if (deliveryStaffRepository.existsByUserId(user.getId())) {
			throw new CustomException(ErrorCode.ALREADY_REGISTERED_DELIVERY_STAFF);
		}

		DeliveryStaff deliveryStaff = DeliveryStaff.builder()
			.userId(user.getId())
			.username(requestDto.username())
			.slackId(requestDto.slackId())
			.deliveryType(requestDto.deliveryType())
			.hubId(requestDto.hubId())
			.build();

		deliveryStaffRepository.save(deliveryStaff);
	}

	@Transactional(readOnly = true)
	public List<UUID> getHubDeliveryStaffList() {
		return deliveryStaffRepository.findByDeliveryType(DeliveryType.HUB_DELIVERY_STAFF)
			.stream()
			.map(DeliveryStaff::getId)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<UUID> getCompanyDeliveryStaffList(UUID hubId) {
		return deliveryStaffRepository.findByDeliveryTypeAndHubId(DeliveryType.COMPANY_DELIVERY_STAFF, hubId)
			.stream()
			.map(DeliveryStaff::getId)
			.toList();
	}

	private static void checkUserRole(String userRole) {
		if (!"MASTER".equals(userRole)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
	}

	private int countHubDeliveryStaff() {
		return deliveryStaffRepository.countByDeliveryType(DeliveryType.HUB_DELIVERY_STAFF);
	}

	private static void checkHubDeliveryStaffCount(int hubDeliveryStaffCount) {
		if (hubDeliveryStaffCount >= 10) {
			throw new CustomException(ErrorCode.NO_ROOM_FOR_HUB_DELIVERY_STAFF);
		}
	}

	private static void checkCompanyDeliveryStaffCount(int companyDeliveryStaffCount) {
		if (companyDeliveryStaffCount >= 10) {
			throw new CustomException(ErrorCode.NO_ROOM_FOR_COMPANY_DELIVERY_STAFF);
		}
	}

	private int countCompanyDeliveryStaff(DeliveryStaffRegisterRequestDto requestDto) {
		return deliveryStaffRepository.countByDeliveryTypeAndHubId(requestDto.deliveryType(),
			requestDto.hubId());
	}

	private User getUser(DeliveryStaffRegisterRequestDto requestDto) {
		return authRepository.findByUsername(Username.of(requestDto.username()))
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
