package com.icarosantos.developer_registration_api.repository;

import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperPJRepository extends JpaRepository<DeveloperCLT, Long> {
}
