package com.example.company.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "product")
public interface ProductClient {

    @DeleteMapping("/api/products")
    ResponseEntity<?> deleteAllProductsByCompanyId(@RequestBody Map<String, Object> body);

}
