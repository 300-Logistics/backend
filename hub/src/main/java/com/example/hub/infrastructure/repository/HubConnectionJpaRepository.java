package com.example.hub.infrastructure.repository;

import com.example.hub.domain.model.entity.HubConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubConnectionJpaRepository extends JpaRepository<HubConnection, UUID> {
}
