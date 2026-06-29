package com.icarosantos.developer_registration_api.service.strategy;

import com.icarosantos.developer_registration_api.model.TypeContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractStrategyResolver {

    private final List<ContractStrategy> strategies;

    public ContractStrategy resolve(TypeContract typeContract) {
        return strategies.stream()
                .filter(strategy -> strategy.getType() == typeContract)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Strategy não encontrada"));

    }
}
