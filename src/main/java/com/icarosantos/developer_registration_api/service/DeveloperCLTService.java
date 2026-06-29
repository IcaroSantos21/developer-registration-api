package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.dto.DeveloperCLTRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.repository.DeveloperCLTRepository;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategyResolver;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de desenvolvedores com contrato CLT.
 * Aplica os padrões Strategy, Factory, Builder, Facade e Observer.
 */
@Service
public class DeveloperCLTService {

    @Autowired
    private DeveloperCLTRepository developerCLTRepository;

    @Autowired
    private DeveloperServiceSupport developerServiceSupport;

    @Autowired
    private ContractStrategyResolver contractStrategyResolver;

    /**
     * Cadastra um novo desenvolvedor CLT.
     * Busca o endereço via ViaCEP, aplica as regras contratuais via Strategy e persiste no banco.
     *
     * @param developerRequest dados do desenvolvedor a ser cadastrado
     */
    public void create(DeveloperCLTRequest developerRequest) {

        Address address = developerServiceSupport.fetchAddress(developerRequest.getCep());

        ContractStrategy contractStrategy = contractStrategyResolver.resolve(TypeContract.CLT);

        boolean hasThirteenSalary = contractStrategy.hasThirteenSalary();
        boolean hasPaidVacation = contractStrategy.hasPaidVacation();

        DeveloperCLT developerCLT = DeveloperCLT.builder()
                .firstName(developerRequest.getFirstName())
                .lastName(developerRequest.getLastName())
                .birthDate(developerRequest.getBirthDate())
                .enterprise(developerRequest.getEnterprise())
                .salary(developerRequest.getSalary())
                .typeDeveloper(developerRequest.getTypeDeveloper())
                .typeContract(TypeContract.CLT)
                .vacationDate(developerRequest.getVacationDate())
                .address(address)
                .admissionDate(developerRequest.getAdmissionDate())
                .thirteenSalary(hasThirteenSalary)
                .paidVacation(hasPaidVacation)
                .build();

        developerCLTRepository.save(developerCLT);

        developerServiceSupport.publishRegistrationEvent(developerCLT);

    }

    /**
     * Retorna todos os desenvolvedores CLT cadastrados.
     *
     * @return lista de {@link DeveloperResponse} com os dados de todos os desenvolvedores CLT
     */
    public List<DeveloperResponse> findAll() {
        var listDevelopers = developerCLTRepository.findAll();
        return listDevelopers.stream().map(this::toResponse).toList();
    }

    /**
     * Busca um desenvolvedor CLT pelo seu identificador.
     *
     * @param id identificador do desenvolvedor
     * @return {@link DeveloperResponse} com os dados do desenvolvedor encontrado
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public DeveloperResponse findById(Long id) {
         DeveloperResponse developerResponse = toResponse(developerCLTRepository.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));
         return developerResponse;
    }

    /**
     * Atualiza os dados de um desenvolvedor CLT existente.
     *
     * @param id identificador do desenvolvedor a ser atualizado
     * @param developerRequest novos dados do desenvolvedor
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public void update(Long id, DeveloperCLTRequest developerRequest) {
        DeveloperCLT developerCLT = findEntityById(id);

        Address address = developerServiceSupport.fetchAddress(developerRequest.getCep());

        ContractStrategy contractStrategy = contractStrategyResolver.resolve(TypeContract.CLT);

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

    /**
     * Remove um desenvolvedor CLT pelo seu identificador.
     *
     * @param id identificador do desenvolvedor a ser removido
     * @throws EntityNotFoundException se nenhum desenvolvedor for encontrado com o id informado
     */
    public void delete(Long id) {
        DeveloperCLT developerCLT = findEntityById(id);

        developerCLTRepository.delete(developerCLT);
    }

    /**
     * Converte uma entidade {@link DeveloperCLT} para um {@link DeveloperResponse}.
     * Calcula os benefícios totais via Strategy.
     *
     * @param developerCLT entidade a ser convertida
     * @return {@link DeveloperResponse} com os dados formatados para resposta
     */
    public DeveloperResponse toResponse(DeveloperCLT developerCLT) {
        ContractStrategy contractStrategy = contractStrategyResolver.resolve(developerCLT.getTypeContract());

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
