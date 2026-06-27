package com.icarosantos.developer_registration_api.controller;

import com.icarosantos.developer_registration_api.dto.DeveloperPJRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.DeveloperPJ;
import com.icarosantos.developer_registration_api.service.DeveloperPJService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DeveloperPJController {

    private final DeveloperPJService developerPJService;

    @PostMapping("/pj")
    public ResponseEntity create(@Valid @RequestBody DeveloperPJRequest developerRequest) {

        developerPJService.create(developerRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/pj")
    public ResponseEntity<List<DeveloperResponse>> findAll() {
        return ResponseEntity.ok(developerPJService.findAll());
    }

    @GetMapping("/pj/{id}")
    public ResponseEntity<DeveloperResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(developerPJService.findById(id));
    }

    @PutMapping("/pj/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody DeveloperPJRequest developerRequest) {
        developerPJService.update(id, developerRequest);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/pj/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        developerPJService.delete(id);
        return ResponseEntity.status(204).build();
    }


}
