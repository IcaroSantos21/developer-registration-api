package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperPJRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperPJ;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.repository.DeveloperPJRepository;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategyResolver;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de desenvolvedores com contrato PJ.
 * Aplica os padrões Strategy, Factory, Builder, Facade e Observer.
 */
@Service
public class DeveloperPJService {
    @Autowired
    DeveloperPJRepository developerPJRepository;

    @Autowired
    private DeveloperServiceSupport developerServiceSupport;
    @Autowired
    private ContractStrategyResolver contractStrategyResolver;

    /**
     * Cadastra um novo desenvolvedor PJ.
     * Busca o endereço via ViaCEP, aplica as regras contratuais via Strategy e persiste no banco.
     *
     * @param developerRequest dados do desenvolvedor a ser cadastrado
     */
    public void create(DeveloperPJRequest developerRequest) {
        Address address = developerServiceSupport.fetchAddress(developerRequest.getCep());

        ContractStrategy contractStrategy = contractStrategyResolver.resolve(TypeContract.PJ);

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

        developerPJRepository.save(developerPJ);

        developerServiceSupport.publishRegistrationEvent(developerPJ);
    }

    /**
     * Busca um desenvolvedor PJ pelo seu identificador.
     *
     * @param id identificador do desenvolvedor
     * @return {@link DeveloperResponse} com os dados do desenvolvedor encontrado
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public DeveloperResponse findById(Long id){
        return toResponse(developerPJRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));
    }

    /**
     * Retorna todos os desenvolvedores PJ cadastrados.
     *
     * @return lista de {@link DeveloperResponse} com os dados de todos os desenvolvedores PJ
     */
    public List<DeveloperResponse> findAll() {
        var listDevelopers = developerPJRepository.findAll();
        return listDevelopers.stream().map(this::toResponse).toList();
    }

    /**
     * Atualiza os dados de um desenvolvedor PJ existente.
     *
     * @param id identificador do desenvolvedor a ser atualizado
     * @param developerRequest novos dados do desenvolvedor
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public void update(Long id, DeveloperPJRequest developerRequest) {
        DeveloperPJ developerPJ = findEntityById(id);

        Address address = developerServiceSupport.fetchAddress(developerRequest.getCep());

        ContractStrategy contractStrategy = contractStrategyResolver.resolve(TypeContract.PJ);

        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        developerPJ.setEnterprise(developerRequest.getEnterprise());
        developerPJ.setSalary(developerRequest.getSalary());
        developerPJ.setVacationDate(developerRequest.getVacationDate());
        developerPJ.setAddress(address);
        developerPJ.setPaidVacation(hasPaidVacation);
        developerPJ.setContractPeriod(developerRequest.getContractPeriod());

        developerPJRepository.save(developerPJ);
    }

    /**
     * Remove um desenvolvedor PJ pelo seu identificador.
     *
     * @param id identificador do desenvolvedor a ser removido
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public void delete(Long id) {
        DeveloperPJ developerPJ = findEntityById(id);

        developerPJRepository.delete(developerPJ);
    }

    /**
     * Converte uma entidade {@link DeveloperPJ} para um {@link DeveloperResponse}.
     * Calcula os benefícios totais e a data de fim do contrato via Strategy.
     *
     * @param developerPJ entidade a ser convertida
     * @return {@link DeveloperResponse} com os dados formatados para resposta
     */
    public DeveloperResponse toResponse(DeveloperPJ developerPJ) {
        ContractStrategy contractStrategy = contractStrategyResolver.resolve(developerPJ.getTypeContract());

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
