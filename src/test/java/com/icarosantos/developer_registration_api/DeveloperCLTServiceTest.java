package com.icarosantos.developer_registration_api;

import com.icarosantos.developer_registration_api.dto.DeveloperCLTRequest;
import com.icarosantos.developer_registration_api.dto.DeveloperResponse;
import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.DeveloperCLT;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import com.icarosantos.developer_registration_api.integration.viacep.ViaCepResponse;
import com.icarosantos.developer_registration_api.repository.DeveloperCLTRepository;
import com.icarosantos.developer_registration_api.service.DeveloperCLTService;
import com.icarosantos.developer_registration_api.service.DeveloperServiceSupport;
import com.icarosantos.developer_registration_api.service.strategy.CltStrategy;
import com.icarosantos.developer_registration_api.service.strategy.ContractStrategyResolver;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperCLTServiceTest {

    @Mock
    private DeveloperServiceSupport developerServiceSupport;

    @Mock
    private DeveloperCLTRepository developerCLTRepository;

    @Mock
    private ContractStrategyResolver contractStrategyResolver;

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
        Address address = viaCepResponse.toAddress();

        when(contractStrategyResolver.resolve(TypeContract.CLT)).thenReturn(new CltStrategy());
        when(developerServiceSupport.fetchAddress("06843160")).thenReturn(address);

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

        when(contractStrategyResolver.resolve(TypeContract.CLT)).thenReturn(new CltStrategy());
        when(developerCLTRepository.findById(1L)).thenReturn(Optional.of(developerCLT));

        // Act - chama o metodo
        DeveloperResponse developerResponse = developerCLTService.findById(1L);

        // Assert - verifica o resultado
        assertEquals(developerResponse.getFullName(), "Icaro Rodrigues");
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        // Arrange - Monta os dados e os mocks
        when(developerCLTRepository.findById(99L)).thenReturn(Optional.empty());

        // Act - chama o metodo
        // Assert - verifica o resultado

        assertThrows(EntityNotFoundException.class, () ->
                developerCLTService.findById(99L));
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

        when(contractStrategyResolver.resolve(TypeContract.CLT)).thenReturn(new CltStrategy());
        when(developerCLTRepository.findAll()).thenReturn(List.of(developerCLT));

        // Act - chama o metodo
        var list = developerCLTService.findAll();

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
        developerCLTService.delete(1L);

        // Assert - verifica o resultado
        verify(developerCLTRepository).delete(any(DeveloperCLT.class));
    }

    @Test
    void shouldUpdateDeveloperCLTSuccessfully() {
        // Arrange - Monta os dados e os mocks
        DeveloperCLTRequest developerRequest = new DeveloperCLTRequest();
        developerRequest.setFirstName("Icaro");
        developerRequest.setLastName("Rodrigues Santos");
        developerRequest.setBirthDate(LocalDate.of(2006, 12, 13));
        developerRequest.setEnterprise("Santander");
        developerRequest.setSalary(new BigDecimal("6700"));
        developerRequest.setTypeDeveloper(TypeDeveloper.BACK);
        developerRequest.setVacationDate(LocalDate.of(2028, 06, 28));
        developerRequest.setCep("06843160");
        developerRequest.setAdmissionDate(LocalDate.of(2026, 06, 27));

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

        when(contractStrategyResolver.resolve(TypeContract.CLT)).thenReturn(new CltStrategy());
        when(developerServiceSupport.fetchAddress("06843160")).thenReturn(address);
        when(developerCLTRepository.findById(1L)).thenReturn(Optional.of(developerCLT));

        // Act - chama o metodo
        developerCLTService.update(1L, developerRequest);

        // Assert - verifica o resultado
        verify(developerCLTRepository).save(any(DeveloperCLT.class));
    }
}
