package com.example.auth.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.domain.model.entity.DeliveryStaff;

public interface DeliveryStaffRepository extends JpaRepository<DeliveryStaff, UUID> {
	boolean existsByUserId(UUID id);
}
