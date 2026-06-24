package com.icarosantos.developer_registration_api.controller;

import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import com.icarosantos.developer_registration_api.service.DeveloperCLTService;
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
    public ResponseEntity create(@RequestBody DeveloperRequest developerRequest) {
        developerCLTService.create(developerRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/clt")
    public ResponseEntity<List<DeveloperCLT>> findAll() {
        return ResponseEntity.ok(developerCLTService.findAll());
    }

    @GetMapping("/clt/{id}")
    public ResponseEntity<DeveloperCLT> findById(@PathVariable Long id) {
        return ResponseEntity.ok(developerCLTService.findById(id).get());
    }

    @PutMapping("/clt/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody DeveloperRequest developerRequest) {
        developerCLTService.update(id, developerRequest);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/clt/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        developerCLTService.delete(id);

        return ResponseEntity.status(204).build();
    }
}


