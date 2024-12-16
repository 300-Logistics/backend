package com.example.auth.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.domain.model.entity.User;
import com.example.auth.domain.model.vo.Username;

public interface AuthRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsername(Username username);

}
