package com.example.slack.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.slack.client.DeliveryStaffClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackUserService {

	private final DeliveryStaffClient deliveryStaffClient;

	public Optional<String> findSlackId(UUID deliveryStaffId) {
		return deliveryStaffClient.getSlackId(deliveryStaffId);
	}
}
