package com.example.delivery.application.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.delivery.infrastructure.client.DeliveryStaffClient;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignDeliveryStaffServiceImpl implements AssignDeliveryStaffService{

	private final DeliveryStaffClient deliveryStaffClient;

	private int hubStaffIndex = 0;
	private final Map<UUID, Integer> companyStaffIndex = new ConcurrentHashMap<>();

	@Override
	public UUID assignHubDeliveryStaff() {
		List<UUID> hubDeliveryStaffList = deliveryStaffClient.getHubDeliveryStaffList();

		checkExistsHubDeliveryStaffList(hubDeliveryStaffList);

		UUID assignedHubStaff = hubDeliveryStaffList.get(hubStaffIndex);
		hubStaffIndex = (hubStaffIndex + 1) % hubDeliveryStaffList.size();

		log.info("hub staff : {}", assignedHubStaff);
		return assignedHubStaff;
	}

	@Override
	public UUID assignCompanyDeliveryStaff(UUID destinationHubId) {
		List<UUID> companyDeliveryStaffList = deliveryStaffClient.getCompanyDeliveryStaffList(destinationHubId);

		checkExistsCompanyDeliveryStaffList(companyDeliveryStaffList);

		int index = companyStaffIndex.getOrDefault(destinationHubId, 0);
		UUID assignedCompanyStaff = companyDeliveryStaffList.get(index);
		companyStaffIndex.put(destinationHubId, (index + 1) % companyDeliveryStaffList.size());

		log.info("company staff : {}", assignedCompanyStaff);
		return assignedCompanyStaff;
	}

	private static void checkExistsCompanyDeliveryStaffList(List<UUID> companyDeliveryStaffList) {
		if (companyDeliveryStaffList.isEmpty()) {
			throw new CustomException(ErrorCode.COMPANY_DELIVERY_STAFF_NOT_FOUND);
		}
	}

	private static void checkExistsHubDeliveryStaffList(List<UUID> hubDeliveryStaffList) {
		if (hubDeliveryStaffList.isEmpty()) {
			throw new CustomException(ErrorCode.HUB_DELIVERY_STAFF_NOT_FOUND);
		}
	}
}
