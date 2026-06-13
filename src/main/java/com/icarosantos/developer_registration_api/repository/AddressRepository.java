package com.icarosantos.developer_registration_api.repository;

import com.icarosantos.developer_registration_api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
