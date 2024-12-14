package com.example.delivery.application.service.interfaces;

import java.util.UUID;

public interface DeliveryFacadeService {

	void updateDeliveryStatusAndNotifyToSlack(UUID deliveryId, UUID userId, String userRole);

}
