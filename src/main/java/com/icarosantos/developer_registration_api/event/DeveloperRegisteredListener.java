package com.icarosantos.developer_registration_api.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component
public class DeveloperRegisteredListener {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperRegisteredListener.class);

    @EventListener
    public void handleDeveloperRegistration(DeveloperRegisteredEvent event) {
        logger.info("\nDev cadastrado: \n" +
                "Nome Completo: {}\n" +
                "Empresa: {}\n" +
                "Contrato: {}\n",
                event.getFullName(), event.getEnterprise(), event.getTypeContract());
    }
}
