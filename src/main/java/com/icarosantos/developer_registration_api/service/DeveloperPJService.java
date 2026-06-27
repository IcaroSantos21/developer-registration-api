package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperPJRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperPJ;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepResponse;
import com.icarosantos.developer_registration_api.event.DeveloperRegisteredEvent;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepFacade;
import com.icarosantos.developer_registration_api.service.factory.ContractFactory;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.repository.DeveloperPJRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DeveloperPJService {
    @Autowired
    ViaCepFacade viaCepFacade;

    @Autowired
    DeveloperPJRepository developerPJRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void create(DeveloperPJRequest developerRequest) {
        // Pegando o endereço do usuario
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        // Definindo o strategy
        ContractStrategy contractStrategy = ContractFactory.create(TypeContract.PJ);

        // Pegando os dados do strategy
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        DeveloperPJ developerPJ = DeveloperPJ.builder()
                .firstName(developerRequest.getFirstName())
                .lastName(developerRequest.getLastName())
                .birthDate(developerRequest.getBirthDate())
                .enterprise(developerRequest.getEnterprise())
                .salary(developerRequest.getSalary())
                .typeDeveloper(developerRequest.getTypeDeveloper())
                .typeContract(TypeContract.PJ)
                .vacationDate(developerRequest.getVacationDate())
                .address(address)
                .contractStartDate(developerRequest.getContractStartDate())
                .contractPeriod(developerRequest.getContractPeriod())
                .paidVacation(hasPaidVacation)
                .build();

        // Salvando no repositório
        developerPJRepository.save(developerPJ);

        applicationEventPublisher.publishEvent(new DeveloperRegisteredEvent(
                developerPJ.getFirstName() + " " + developerPJ.getLastName(),
                developerPJ.getEnterprise(),
                developerPJ.getTypeContract()
        ));
    }

    public DeveloperResponse findById(Long id){
        return toResponse(developerPJRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));
    }

    public List<DeveloperResponse> findAll() {
        var listDevelopers = developerPJRepository.findAll();
        return listDevelopers.stream().map(this::toResponse).toList();
    }

    public void update(Long id, DeveloperPJRequest developerRequest) {
        DeveloperPJ developerPJ = findEntityById(id);
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        ContractStrategy contractStrategy = ContractFactory.create(TypeContract.PJ);

        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        developerPJ.setEnterprise(developerRequest.getEnterprise());
        developerPJ.setSalary(developerRequest.getSalary());
        developerPJ.setVacationDate(developerRequest.getVacationDate());
        developerPJ.setAddress(address);
        developerPJ.setPaidVacation(hasPaidVacation);
        developerPJ.setContractPeriod(developerRequest.getContractPeriod());

        developerPJRepository.save(developerPJ);
    }

    public void delete(Long id) {
        DeveloperPJ developerPJ = findEntityById(id);

        developerPJRepository.delete(developerPJ);
    }

    public DeveloperResponse toResponse(DeveloperPJ developerPJ) {
        ContractStrategy contractStrategy = ContractFactory.create(developerPJ.getTypeContract());

        BigDecimal totalBenefits = contractStrategy.calculateTotalBenefits(developerPJ.getSalary());
        LocalDate contractEndDate = contractStrategy.calculateContractEndDate(developerPJ.getContractStartDate(), developerPJ.getContractPeriod());

        return DeveloperResponse.builder()
                .id(developerPJ.getId())
                .fullName(developerPJ.getFirstName() + " " + developerPJ.getLastName())
                .enterprise(developerPJ.getEnterprise())
                .salary(developerPJ.getSalary())
                .typeDeveloper(developerPJ.getTypeDeveloper())
                .address(developerPJ.getAddress())
                .vacationDate(developerPJ.getVacationDate())
                .totalBenefits(totalBenefits)
                .contractEndDate(contractEndDate)
                .build();
    }

    private DeveloperPJ findEntityById (Long id) {
        return developerPJRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

}
