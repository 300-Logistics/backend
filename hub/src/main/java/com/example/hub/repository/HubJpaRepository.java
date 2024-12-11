package com.example.hub.repository;

import com.example.hub.domain.model.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<Hub, Long> {
}
