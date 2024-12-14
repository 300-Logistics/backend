package com.example.delivery.application.service;

import java.util.UUID;

public interface DeliveryFacadeService {

	void updateDeliveryStatusAndNotifyToSlack(UUID deliveryId);

}
