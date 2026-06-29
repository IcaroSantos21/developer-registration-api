package com.icarosantos.developer_registration_api.service.factory;

import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.service.strategy.CltStrategy;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.service.strategy.PjStrategy;

/**
 * Factory responsável por instanciar a {@link ContractStrategy} correta com base no tipo de contrato.
 * Aplica o padrão Factory Method para desacoplar a criação das Strategies de quem as utiliza.
 */
public class ContractFactory {

    /**
     * Cria e retorna a Strategy correspondente ao tipo de contrato informado.
     *
     * @param typeContract tipo de contrato do desenvolvedor
     * @return implementação de {@link ContractStrategy} correspondente ao tipo informado
     * @throws IllegalArgumentException se o tipo de contrato não for suportado
     */
    public static ContractStrategy create(TypeContract typeContract) {
        switch (typeContract) {
            case PJ:
                return new PjStrategy();
            case CLT:
                return new CltStrategy();
            default:
                throw new IllegalArgumentException("Tipo de contrato não suportado: " + typeContract);
        }
    }
}
