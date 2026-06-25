package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import com.icarosantos.developer_registration_api.patterns.adapter.ViaCepResponse;
import com.icarosantos.developer_registration_api.patterns.facade.ViaCepFacade;
import com.icarosantos.developer_registration_api.patterns.factory.ContractFactory;
import com.icarosantos.developer_registration_api.patterns.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.repository.DeveloperCLTRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class DeveloperCLTService {
    @Autowired
    private ViaCepFacade viaCepFacade;

    @Autowired
    private DeveloperCLTRepository developerCLTRepository;

    // Metodo para criar um novo usuário
    public void create(DeveloperRequest developerRequest) {
        // Pegando o endereço do usuário
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        // Definindo o Strategy
        ContractStrategy contractStrategy = ContractFactory.create(developerRequest.getTypeContract());

        // Pegando hasThirteenSalary e o hasPaidVacation
        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        // Montando a entidade
        DeveloperCLT developerCLT = DeveloperCLT.builder()
                .firstName(developerRequest.getFirstName())
                .lastName(developerRequest.getLastName())
                .birthDate(developerRequest.getBirthDate())
                .enterprise(developerRequest.getEnterprise())
                .salary(developerRequest.getSalary())
                .typeDeveloper(developerRequest.getTypeDeveloper())
                .typeContract(developerRequest.getTypeContract())
                .vacationDate(developerRequest.getVacationDate())
                .address(address)
                .admissionDate(developerRequest.getAdmissionDate())
                .thirteenSalary(hasThirteenSalary)
                .paidVacation(hasPaidVacation)
                .build();

        // Salvando no repository
        developerCLTRepository.save(developerCLT);
    }

    public List<DeveloperResponse> findAll() {
        var listDevelopers = developerCLTRepository.findAll();
        return listDevelopers.stream().map(this::toResponse).toList();
    }

    public DeveloperResponse findById(Long id) {
         DeveloperResponse developerResponse = toResponse(developerCLTRepository.findById(id).orElseThrow());
         return developerResponse;
    }

    public void update(Long id, DeveloperRequest developerRequest) {
        DeveloperCLT developerCLT = findEntityById(id);

        if (developerCLT.getTypeContract() != developerRequest.getTypeContract()) throw new
                IllegalArgumentException("Tipo de Contrato não pode ser alterado");

        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        ContractStrategy contractStrategy = ContractFactory.create(developerRequest.getTypeContract());

        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        developerCLT.setEnterprise(developerRequest.getEnterprise());
        developerCLT.setSalary(developerRequest.getSalary());
        developerCLT.setVacationDate(developerRequest.getVacationDate());
        developerCLT.setAddress(address);
        developerCLT.setThirteenSalary(hasThirteenSalary);
        developerCLT.setPaidVacation(hasPaidVacation);

        developerCLTRepository.save(developerCLT);
    }

    public void delete(Long id) {
        DeveloperCLT developerCLT = findEntityById(id);

        developerCLTRepository.delete(developerCLT);
    }

    public DeveloperResponse toResponse(DeveloperCLT developerCLT) {
        ContractStrategy contractStrategy = ContractFactory.create(developerCLT.getTypeContract());

        BigDecimal totalBenefits = contractStrategy.calculateTotalBenefits(developerCLT.getSalary());

        return DeveloperResponse.builder()
                .id(developerCLT.getId())
                .fullName(developerCLT.getFirstName() + " " + developerCLT.getLastName())
                .enterprise(developerCLT.getEnterprise())
                .salary(developerCLT.getSalary())
                .typeDeveloper(developerCLT.getTypeDeveloper())
                .address(developerCLT.getAddress())
                .vacationDate(developerCLT.getVacationDate())
                .totalBenefits(totalBenefits)
                .build();
    }

    private DeveloperCLT findEntityById(Long id) {
        return developerCLTRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
