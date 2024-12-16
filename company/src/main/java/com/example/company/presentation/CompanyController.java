package com.example.company.presentation;

import com.example.company.application.dto.CompanyDto;
import com.example.company.application.service.CompanyService;
import com.example.company.presentation.request.CompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CompanyRequest companyRequest) {
        UUID companyId = companyService.create(companyRequest);
        return ResponseEntity.created(URI.create(companyId.toString())).build();
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<?> put(@PathVariable UUID companyId, @RequestBody CompanyRequest companyRequest) {
        CompanyDto companyDto = companyService.update(companyRequest, companyId);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> delete(@PathVariable UUID companyId) {
        companyService.delete(companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<?> get(@PathVariable UUID companyId) {
        CompanyDto companyDto = companyService.find(companyId);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value = "sortBy", defaultValue = "createdBy") String sortBy) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        if (page <= 0) {
            page = 1;
        }
        if (!(sortBy.equals("createdBy") || sortBy.equals("updatedBy"))) {
            sortBy = "createdBy";
        }

        Page<CompanyDto> companyDtos = companyService.find(keyword, page - 1, size, sortBy);
        return ResponseEntity.ok(companyDtos);
    }


}
