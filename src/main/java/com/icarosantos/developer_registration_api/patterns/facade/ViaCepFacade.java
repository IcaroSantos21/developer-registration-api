package com.icarosantos.developer_registration_api.patterns.facade;

import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.patterns.Addapter.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepFacade {

    @GetMapping("/{cep}/json/")
    ViaCepResponse getAddress(@PathVariable String cep);
}