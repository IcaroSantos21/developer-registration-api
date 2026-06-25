package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
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
                .vacationDate(developerRequest.getHolidayDate())
                .address(address)
                .admissionDate(developerRequest.getAdmissionDate())
                .thirteenSalary(hasThirteenSalary)
                .paidVacation(hasPaidVacation)
                .build();

        // Salvando no repository
        developerCLTRepository.save(developerCLT);
    }

    public List<DeveloperCLT> findAll() {
        return developerCLTRepository.findAll();
    }

    public Optional<DeveloperCLT> findById(Long id) {
        return developerCLTRepository.findById(id);
    }

    public void update(Long id, DeveloperRequest developerRequest) {
        Optional<DeveloperCLT> developerCLT = findById(id);

        if (developerCLT.isEmpty()) throw new EntityNotFoundException("Usuário não encontrado");
        if (developerCLT.get().getTypeContract() != developerRequest.getTypeContract()) throw new
                IllegalArgumentException("Tipo de Contrato não pode ser alterado");

        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(developerRequest.getCep());
        Address address = viaCepResponse.toAddress();

        ContractStrategy contractStrategy = ContractFactory.create(developerRequest.getTypeContract());

        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        developerCLT.get().setEnterprise(developerRequest.getEnterprise());
        developerCLT.get().setSalary(developerRequest.getSalary());
        developerCLT.get().setVacationDate(developerRequest.getHolidayDate());
        developerCLT.get().setAddress(address);
        developerCLT.get().setThirteenSalary(hasThirteenSalary);
        developerCLT.get().setPaidVacation(hasPaidVacation);

        developerCLTRepository.save(developerCLT.get());
    }

    public void delete(Long id) {
        Optional<DeveloperCLT> developerCLT = findById(id);
        if (developerCLT.isEmpty()) throw new EntityNotFoundException("Usuário não encontrado");

        developerCLTRepository.delete(developerCLT.get());
    }
}
