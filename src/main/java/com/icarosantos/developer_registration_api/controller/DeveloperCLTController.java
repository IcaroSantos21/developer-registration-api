package com.icarosantos.developer_registration_api.controller;

import com.icarosantos.developer_registration_api.dto.DeveloperCLTRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.service.DeveloperCLTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DeveloperCLTController {

    private final DeveloperCLTService developerCLTService;

    @PostMapping("/clt")
    public ResponseEntity create(@Valid @RequestBody DeveloperCLTRequest developerRequest) {
        developerCLTService.create(developerRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/clt")
    public ResponseEntity<List<DeveloperResponse>> findAll() {
        return ResponseEntity.ok(developerCLTService.findAll());
    }

    @GetMapping("/clt/{id}")
    public ResponseEntity<DeveloperResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(developerCLTService.findById(id));
    }

    @PutMapping("/clt/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody DeveloperCLTRequest developerRequest) {
        developerCLTService.update(id, developerRequest);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/clt/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        developerCLTService.delete(id);

        return ResponseEntity.status(204).build();
    }
}


