package com.icarosantos.developer_registration_api.integration.viacep;

import com.icarosantos.developer_registration_api.model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa a resposta da API externa ViaCEP.
 * Aplica o padrão Adapter para mapear os campos em português do ViaCEP
 * para o modelo {@link Address} utilizado internamente pela aplicação.
 */
@Getter
@Setter
@NoArgsConstructor
public class ViaCepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String estado;

    /**
     * Converte a resposta do ViaCEP para o modelo interno {@link Address}.
     *
     * @return {@link Address} com os campos mapeados do padrão ViaCEP para o padrão da aplicação.
     */
    public Address toAddress() {
        return new Address(
                this.cep, this.logradouro, this.complemento,
                this.bairro, this.localidade, this.estado);
    }
}
