package com.icarosantos.developer_registration_api.service.strategy;

import com.icarosantos.developer_registration_api.model.TypeContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Componente responsável por resolver a {@link ContractStrategy} correta com base no tipo de contrato.
 * Utiliza injeção de dependências do Spring para gerenciar as implementações disponíveis,
 * eliminando a necessidade de instanciação manual.
 */
@Service
@RequiredArgsConstructor
public class ContractStrategyResolver {

    private final List<ContractStrategy> strategies;


    /**
     * Resolve a {@link ContractStrategy} correspondente ao tipo de contrato informado.
     *
     * @param typeContract tipo de contrato do desenvolvedor
     * @return implementação de {@link ContractStrategy} correspondente ao tipo informado.
     * @throws IllegalArgumentException se nenhuma Strategy for encontrada para o tipo informado.
     */
    public ContractStrategy resolve(TypeContract typeContract) {
        return strategies.stream()
                .filter(strategy -> strategy.getType() == typeContract)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Strategy não encontrada"));

    }
}
