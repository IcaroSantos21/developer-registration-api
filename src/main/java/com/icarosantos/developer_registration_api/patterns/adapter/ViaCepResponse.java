package com.icarosantos.developer_registration_api.patterns.adapter;

import com.icarosantos.developer_registration_api.model.Address;
import lombok.Getter;

@Getter
public class ViaCepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String estado;


    public Address toAddress() {
        return new Address(
                this.cep, this.logradouro, this.complemento,
                this.bairro, this.localidade, this.estado);
    }
}
