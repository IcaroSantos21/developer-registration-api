package com.icarosantos.developer_registration_api.service;

import com.icarosantos.developer_registration_api.event.DeveloperRegisteredEvent;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepFacade;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.Developer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeveloperServiceSupport {

    private final ViaCepFacade viaCepFacade;

    private final ApplicationEventPublisher applicationEventPublisher;


    public Address fetchAddress(String cep) {
        ViaCepResponse viaCepResponse = viaCepFacade.getAddress(cep);
        return viaCepResponse.toAddress();
    }

    public void publishRegistrationEvent(Developer developer) {
        applicationEventPublisher.publishEvent(new DeveloperRegisteredEvent(
                developer.getFirstName() + " " + developer.getLastName(),
                developer.getEnterprise(),
                developer.getTypeContract()
        ));
    }
}
