package com.example.hub.infrastructure.repository;

import com.example.hub.domain.model.entity.HubPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubPathJpaRepository extends JpaRepository<HubPath, UUID> {
    HubPath findByStartHubIdAndEndHubIdAndDeletedAtIsNull(UUID startHubId, UUID endHubId);
}
