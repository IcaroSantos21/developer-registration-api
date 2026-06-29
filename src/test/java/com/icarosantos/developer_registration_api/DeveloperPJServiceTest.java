package com.icarosantos.developer_registration_api;

import com.icarosantos.developer_registration_api.dto.DeveloperPJRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.*;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepResponse;
import com.icarosantos.developer_registration_api.repository.DeveloperPJRepository;
import com.icarosantos.developer_registration_api.service.DeveloperPJService;
import com.icarosantos.developer_registration_api.service.DeveloperServiceSupport;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategyResolver;
import com.icarosantos.developer_registration_api.service.strategy.PjStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private DeveloperServiceSupport developerServiceSupport;

    @Mock
    private ContractStrategyResolver contractStrategyResolver;

    @Mock
    private DeveloperPJRepository developerPJRepository;

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
        Address address = viaCepResponse.toAddress();

        when(contractStrategyResolver.resolve(TypeContract.PJ)).thenReturn(new PjStrategy());
        when(developerServiceSupport.fetchAddress("06843160")).thenReturn(address);

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
                .typeContract(TypeContract.PJ)
                .vacationDate(LocalDate.of(2027, 07, 01))
                .address(address)
                .paidVacation(true)
                .contractStartDate(LocalDate.of(2026, 06, 27))
                .contractPeriod(1)
                .build();

        when(contractStrategyResolver.resolve(TypeContract.PJ)).thenReturn(new PjStrategy());
        when(developerPJRepository.findById(1L)).thenReturn(Optional.of(developerPJ));

        // Act - chama o metodo
        DeveloperResponse developerResponse = developerPJService.findById(1L);

        // Assert - verifica o resultado
        assertEquals(developerResponse.getFullName(), "Icaro Rodrigues");
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        // Arrange - Monta os dados e os mocks
        when(developerPJRepository.findById(99L)).thenReturn(Optional.empty());

        // Act - chama o metodo
        // Assert - verifica o resultado

        assertThrows(EntityNotFoundException.class, () ->
                developerPJService.findById(99L));
    }

    @Test
    void shouldReturnListOfDeveloperResponseWhenFindAll() {
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
                .typeContract(TypeContract.PJ)
                .vacationDate(LocalDate.of(2027, 07, 01))
                .address(address)
                .paidVacation(true)
                .contractStartDate(LocalDate.of(2026, 06, 27))
                .contractPeriod(1)
                .build();

        when(contractStrategyResolver.resolve(TypeContract.PJ)).thenReturn(new PjStrategy());
        when(developerPJRepository.findAll()).thenReturn(List.of(developerPJ));

        // Act - chama o metodo
        var list = developerPJService.findAll();

        // Assert - verifica o resultado
        assertEquals(1, list.size());
    }

    @Test
    void shouldDeleteDeveloperCLTWhenIdExists() {
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
                .typeContract(TypeContract.PJ)
                .vacationDate(LocalDate.of(2027, 07, 01))
                .address(address)
                .paidVacation(true)
                .contractStartDate(LocalDate.of(2026, 06, 27))
                .contractPeriod(1)
                .build();

        when(developerPJRepository.findById(1L)).thenReturn(Optional.of(developerPJ));

        // Act - chama o metodo
        developerPJService.delete(1L);

        // Assert - verifica o resultado
        verify(developerPJRepository).delete(any(DeveloperPJ.class));
    }

    @Test
    void shouldUpdateDeveloperCLTSuccessfully() {
        // Arrange - Monta os dados e os mocks
        DeveloperPJRequest developerRequest = new DeveloperPJRequest();
        developerRequest.setFirstName("Icaro");
        developerRequest.setLastName("Rodrigues Santos");
        developerRequest.setBirthDate(LocalDate.of(2006, 12, 13));
        developerRequest.setEnterprise("Santander");
        developerRequest.setSalary(new BigDecimal("6700"));
        developerRequest.setTypeDeveloper(TypeDeveloper.BACK);
        developerRequest.setVacationDate(LocalDate.of(2028, 06, 28));
        developerRequest.setCep("06843160");
        developerRequest.setContractStartDate(LocalDate.of(2026, 06, 27));
        developerRequest.setContractPeriod(1);

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("06843160");
        viaCepResponse.setLogradouro("Rua Padre Antonio");
        viaCepResponse.setComplemento("Casa mais bonita");
        viaCepResponse.setBairro("Maria Auxiliadora");
        viaCepResponse.setLocalidade("Embu das Artes");
        viaCepResponse.setEstado("São Paulo");

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
                .typeContract(TypeContract.PJ)
                .vacationDate(LocalDate.of(2027, 07, 01))
                .address(address)
                .paidVacation(true)
                .contractStartDate(LocalDate.of(2026, 06, 27))
                .contractPeriod(1)
                .build();

        when(developerServiceSupport.fetchAddress("06843160")).thenReturn(address);
        when(contractStrategyResolver.resolve(TypeContract.PJ)).thenReturn(new PjStrategy());
        when(developerPJRepository.findById(1L)).thenReturn(Optional.of(developerPJ));

        // Act - chama o metodo
        developerPJService.update(1L, developerRequest);

        // Assert - verifica o resultado
        verify(developerPJRepository).save(any(DeveloperPJ.class));
    }
}
