package com.example.auth.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.auth.domain.model.entity.DeliveryStaff;
import com.example.auth.domain.model.enums.DeliveryType;

import jakarta.persistence.LockModeType;

public interface DeliveryStaffRepository extends JpaRepository<DeliveryStaff, UUID> {
	boolean existsByUserId(UUID id);

	List<DeliveryStaff> findByDeliveryTypeAndHubId(DeliveryType deliveryType, UUID hubId);

	List<DeliveryStaff> findByDeliveryType(DeliveryType deliveryType);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	int countByDeliveryType(DeliveryType deliveryType);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	int countByDeliveryTypeAndHubId(DeliveryType deliveryType, UUID uuid);
}
