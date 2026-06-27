package com.icarosantos.developer_registration_api.service.factory;

import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.service.strategy.CltStrategy;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategy;
import com.icarosantos.developer_registration_api.service.strategy.PjStrategy;

public class ContractFactory {
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
