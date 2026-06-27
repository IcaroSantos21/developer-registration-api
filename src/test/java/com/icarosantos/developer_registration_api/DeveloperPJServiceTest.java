package com.icarosantos.developer_registration_api;

import com.icarosantos.developer_registration_api.dto.DeveloperPJRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.*;
import com.icarosantos.developer_registration_api.patterns.adapter.ViaCepResponse;
import com.icarosantos.developer_registration_api.patterns.facade.ViaCepFacade;
import com.icarosantos.developer_registration_api.repository.DeveloperPJRepository;
import com.icarosantos.developer_registration_api.service.DeveloperPJService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperPJServiceTest {

    @Mock
    private ViaCepFacade viaCepFacade;

    @Mock
    private DeveloperPJRepository developerPJRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DeveloperPJService developerPJService;

    @Test
    void shouldCreateDeveloperPJSuccessfully() {
        // Arrange - Monta os dados e os mocks
        DeveloperPJRequest developerRequest = new DeveloperPJRequest();
        developerRequest.setFirstName("Icaro");
        developerRequest.setLastName("Rodrigues");
        developerRequest.setBirthDate(LocalDate.of(2006, 12, 13));
        developerRequest.setEnterprise("Santander");
        developerRequest.setSalary(new BigDecimal("5000"));
        developerRequest.setTypeDeveloper(TypeDeveloper.BACK);
        developerRequest.setVacationDate(LocalDate.of(2027, 07, 01));
        developerRequest.setCep("06843160");
        developerRequest.setContractStartDate(LocalDate.of(2026, 06, 27));
        developerRequest.setContractPeriod(2);

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("06843160");
        viaCepResponse.setLogradouro("Rua Padre Antonio");
        viaCepResponse.setComplemento("Casa mais bonita");
        viaCepResponse.setBairro("Maria Auxiliadora");
        viaCepResponse.setLocalidade("Embu das Artes");
        viaCepResponse.setEstado("São Paulo");

        when(viaCepFacade.getAddress("06843160")).thenReturn(viaCepResponse);

        // Act - chama o metodo
        developerPJService.create(developerRequest);

        // Assert - verifica o resultado
        verify(developerPJRepository).save(any(DeveloperPJ.class));
    }

    @Test
    void shouldReturnDeveloperResponseWhenIdExists() {
        // Arrange - Monta os dados e os mocks
        Address address = new Address(
                "06843160",
                "Rua Padre Antonio",
                "Casa mais bonita",
                "Maria Auxiliadora",
                "Embu das Artes",
                "São Paulo");

        DeveloperPJ developerPJ = DeveloperPJ.builder()
                .id(1L)
                .firstName("Icaro")
                .lastName("Rodrigues")
                .birthDate(LocalDate.of(2006, 12, 13))
                .enterprise("AWS")
                .salary(new BigDecimal(10000))
                .typeDeveloper(TypeDeveloper.BACK)
                .typeContract(TypeContract.CLT)
                .vacationDate(LocalDate.of(2027, 07, 01))
                .address(address)
                .paidVacation(true)
                .contractStartDate(LocalDate.of(2026, 06, 27))
                .contractPeriod(1)
                .build();

        when(developerPJRepository.findById(1L)).thenReturn(Optional.of(developerPJ));

        // Act - chama o metodo
        DeveloperResponse developerResponse = developerPJService.findById(1L);

        // Assert - verifica o resultado
        assertEquals(developerResponse.getFullName(), "Icaro Rodrigues");
    }
}
