package com.example.delivery.application.service.interfaces;

import java.util.UUID;

public interface AssignDeliveryStaffService {

	UUID assignHubDeliveryStaff();
	UUID assignCompanyDeliveryStaff(UUID destinationHubId);

}
