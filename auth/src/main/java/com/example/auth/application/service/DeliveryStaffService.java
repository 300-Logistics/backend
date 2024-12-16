package com.example.auth.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.domain.model.entity.DeliveryStaff;
import com.example.auth.domain.model.entity.User;
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
	public void registerDeliveryStaff(DeliveryStaffRegisterRequestDto requestDto) {
		User user = getUser(requestDto);

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

	private User getUser(DeliveryStaffRegisterRequestDto requestDto) {
		return authRepository.findByUsername(Username.of(requestDto.username()))
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
