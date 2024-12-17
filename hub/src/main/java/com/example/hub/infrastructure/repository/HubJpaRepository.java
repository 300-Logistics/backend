package com.example.hub.infrastructure.repository;

import com.example.hub.domain.model.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubJpaRepository extends JpaRepository<Hub, UUID> {
}
