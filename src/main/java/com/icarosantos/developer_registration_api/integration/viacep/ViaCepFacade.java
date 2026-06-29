package com.icarosantos.developer_registration_api.integration.viacep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign responsável pela integração com a API externa ViaCEP.
 * Aplica padrão Facade para abstrair a complexidade da chamada HTTP externa
 */
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepFacade {

    /**
     * Busca os dados de endereço correspondentes ao CEP informado
     *
     * @param cep CEP a ser consultado
     * @return {@link ViaCepResponse} com os dados do endereço retornados pela API
     */
    @GetMapping("/{cep}/json/")
    ViaCepResponse getAddress(@PathVariable String cep);
}