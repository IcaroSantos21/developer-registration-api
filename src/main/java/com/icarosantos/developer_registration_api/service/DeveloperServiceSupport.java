package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.event.DeveloperRegisteredEvent;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepFacade;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.Developer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Componente de suporte aos serviços de desenvolvedor.
 * Centraliza operações comuns entre {@link DeveloperCLTService} e {@link DeveloperPJService}
 * Eliminando duplicação de código
 */
@Service
@RequiredArgsConstructor
public class DeveloperServiceSupport {

    private final ViaCepFacade viaCepFacade;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Busca o endereço completo a partir do CEP informado via API ViaCEP.
     *
     * @param cep CEP a ser consultado.
     * @return {@link Address} com os dados do endereço.
     */
    public Address fetchAddress(String cep) {
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(cep);
        return viaCepResponse.toAddress();
    }

    /**
     * Publica o evento de registro do desenvolvedor para o Observer.
     * Dispara o log de auditoria com nome, empresa e tipo de contrato.
     *
     * @param developer entidade do desenvolvedor recém cadastrado.
     */
    public void publishRegistrationEvent(Developer developer) {
        applicationEventPublisher.publishEvent(new DeveloperRegisteredEvent(
                developer.getFirstName() + " " + developer.getLastName(),
                developer.getEnterprise(),
                developer.getTypeContract()
        ));
    }
}
