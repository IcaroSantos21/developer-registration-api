package com.icarosantos.developer_registration_api;

import com.icarosantos.developer_registration_api.dto.DeveloperCLTRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import com.icarosantos.developer_registration_api.patterns.adapter.ViaCepResponse;
import com.icarosantos.developer_registration_api.patterns.facade.ViaCepFacade;
import com.icarosantos.developer_registration_api.repository.DeveloperCLTRepository;
import com.icarosantos.developer_registration_api.service.DeveloperCLTService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperCLTServiceTest {

    @Mock
    private ViaCepFacade viaCepFacade;

    @Mock
    private DeveloperCLTRepository developerCLTRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DeveloperCLTService developerCLTService;

    @Test
    void shouldCreateDeveloperCLTSuccessfully() {
        // Arrange - Monta os dados e os mocks
        DeveloperCLTRequest developerRequest = new DeveloperCLTRequest();
        developerRequest.setFirstName("Icaro");
        developerRequest.setLastName("Rodrigues");
        developerRequest.setBirthDate(LocalDate.of(2006, 12, 13));
        developerRequest.setEnterprise("Santander");
        developerRequest.setSalary(new BigDecimal("5000"));
        developerRequest.setTypeDeveloper(TypeDeveloper.BACK);
        developerRequest.setVacationDate(LocalDate.of(2027, 07, 01));
        developerRequest.setCep("06843160");
        developerRequest.setAdmissionDate(LocalDate.of(2026, 06, 27));

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("06843160");
        viaCepResponse.setLogradouro("Rua Padre Antonio");
        viaCepResponse.setComplemento("Casa mais bonita");
        viaCepResponse.setBairro("Maria Auxiliadora");
        viaCepResponse.setLocalidade("Embu das Artes");
        viaCepResponse.setEstado("São Paulo");

        when(viaCepFacade.getAddress("06843160")).thenReturn(viaCepResponse);

        // Act - chama o metodo
        developerCLTService.create(developerRequest);

        // Assert - verifica o resultado
        verify(developerCLTRepository).save(any(DeveloperCLT.class));
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

        DeveloperCLT developerCLT = DeveloperCLT.builder()
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
                .admissionDate(LocalDate.of(2026, 06, 27)).
                thirteenSalary(true)
                .build();

        when(developerCLTRepository.findById(1L)).thenReturn(Optional.of(developerCLT));

        // Act - chama o metodo
        DeveloperResponse developerResponse = developerCLTService.findById(1L);

        // Assert - verifica o resultado
        assertEquals(developerResponse.getFullName(), "Icaro Rodrigues");
    }
}
