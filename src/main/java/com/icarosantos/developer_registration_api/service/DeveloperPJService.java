package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperPJ;
import com.icarosantos.developer_registration_api.patterns.adapter.ViaCepResponse;
import com.icarosantos.developer_registration_api.patterns.facade.ViaCepFacade;
import com.icarosantos.developer_registration_api.patterns.factory.ContractFactory;
import com.icarosantos.developer_registration_api.patterns.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.repository.DeveloperPJRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperPJService {
    @Autowired
    ViaCepFacade viaCepFacade;

    @Autowired
    DeveloperPJRepository developerPJRepository;

    public void create(DeveloperRequest developerRequest) {
        // Pegando o endereço do usuario
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        // Definindo o strategy
        ContractStrategy contractStrategy = ContractFactory.create(developerRequest.getTypeContract());

        // Pegando os dados do strategy
        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        DeveloperPJ developerPJ = DeveloperPJ.builder()
                .firstName(developerRequest.getFirstName())
                .lastName(developerRequest.getLastName())
                .birthDate(developerRequest.getBirthDate())
                .enterprise(developerRequest.getEnterprise())
                .salary(developerRequest.getSalary())
                .typeDeveloper(developerRequest.getTypeDeveloper())
                .typeContract(developerRequest.getTypeContract())
                .vacationDate(developerRequest.getHolidayDate())
                .address(address)
                .contractStartDate(developerRequest.getContractStartDate())
                .contractPeriod(developerRequest.getContractPeriod())
                .paidVacation(hasPaidVacation)
                .build();

        // Salvando no repositório
        developerPJRepository.save(developerPJ);
    }

    public Optional<DeveloperPJ> findById(Long id){
        return developerPJRepository.findById(id);
    }

    public List<DeveloperPJ> findAll() {
        return developerPJRepository.findAll();
    }

    public void update(Long id, DeveloperRequest developerRequest) {
        Optional<DeveloperPJ> developerPJ = findById(id);

        if (developerPJ.isEmpty()) throw new EntityNotFoundException("Usuário não encontrado");

        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        ContractStrategy contractStrategy = ContractFactory.create(developerRequest.getTypeContract());

        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        developerPJ.get().setEnterprise(developerRequest.getEnterprise());
        developerPJ.get().setSalary(developerRequest.getSalary());
        developerPJ.get().setTypeContract(developerRequest.getTypeContract());
        developerPJ.get().setVacationDate(developerRequest.getHolidayDate());
        developerPJ.get().setAddress(address);
        developerPJ.get().setPaidVacation(hasPaidVacation);
        developerPJ.get().setContractPeriod(developerRequest.getContractPeriod());

        developerPJRepository.save(developerPJ.get());
    }

    public void delete(Long id) {
        Optional<DeveloperPJ> developerPJ = findById(id);

        if (developerPJ.isEmpty()) throw new EntityNotFoundException("Usuário não encontrado");

        developerPJRepository.delete(developerPJ.get());
    }

}
