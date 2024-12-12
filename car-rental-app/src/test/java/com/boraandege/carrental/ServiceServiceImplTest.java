package com.boraandege.carrental;
import com.boraandege.carrental.dto.ServiceDTO;
import com.boraandege.carrental.model.AdditionalService;
import com.boraandege.carrental.repository.ServiceRepository;
import com.boraandege.carrental.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ServiceServiceImplTest {

    @Autowired
    private ServiceServiceImpl serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    void testAddService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("Roadside Assistance");
        serviceDTO.setPrice(BigDecimal.valueOf(20));

        ServiceDTO result = serviceService.addService(serviceDTO);

        assertNotNull(result);
        assertEquals("Roadside Assistance", result.getName());
        assertEquals(BigDecimal.valueOf(20), result.getPrice());
    }

    @Test
    void testGetServiceById() {
        AdditionalService service = new AdditionalService();
        service.setName("Roadside Assistance");
        service.setPrice(BigDecimal.valueOf(20));

        AdditionalService savedService = serviceRepository.save(service);

        ServiceDTO result = serviceService.getServiceById(savedService.getId());

        assertNotNull(result);
        assertEquals("Roadside Assistance", result.getName());
        assertEquals(BigDecimal.valueOf(20), result.getPrice());
    }

    @Test
    void testGetServiceById_NotFound() {
        assertThrows(Exception.class, () -> serviceService.getServiceById(999L));
    }

    @Test
    void testGetAllServices() {
        AdditionalService service1 = new AdditionalService();
        service1.setName("Roadside Assistance");
        service1.setPrice(BigDecimal.valueOf(20));

        AdditionalService service2 = new AdditionalService();
        service2.setName("Insurance");
        service2.setPrice(BigDecimal.valueOf(15));

        serviceRepository.save(service1);
        serviceRepository.save(service2);

        List<ServiceDTO> result = serviceService.getAllServices();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> "Roadside Assistance".equals(s.getName())));
        assertTrue(result.stream().anyMatch(s -> "Insurance".equals(s.getName())));
    }

    @Test
    void testUpdateService() {
        AdditionalService service = new AdditionalService();
        service.setName("Roadside Assistance");
        service.setPrice(BigDecimal.valueOf(20));

        AdditionalService savedService = serviceRepository.save(service);

        ServiceDTO updatedServiceDTO = new ServiceDTO();
        updatedServiceDTO.setName("Roadside Assistance Updated");
        updatedServiceDTO.setPrice(BigDecimal.valueOf(25));

        ServiceDTO result = serviceService.updateService(savedService.getId(), updatedServiceDTO);

        assertNotNull(result);
        assertEquals("Roadside Assistance Updated", result.getName());
        assertEquals(BigDecimal.valueOf(25), result.getPrice());
    }

    @Test
    void testDeleteService() {
        AdditionalService service = new AdditionalService();
        service.setName("Roadside Assistance");
        service.setPrice(BigDecimal.valueOf(20));

        AdditionalService savedService = serviceRepository.save(service);

        serviceService.deleteService(savedService.getId());

        assertFalse(serviceRepository.findById(savedService.getId()).isPresent());
    }
}
