package com.example.hub.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthClient {

    @PostMapping("/api/auth/validate")
    ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token);

    @PostMapping("/api/auth/validate/master")
    ResponseEntity<Boolean> validateMasterToken(@RequestHeader("Authorization") String token);

}
