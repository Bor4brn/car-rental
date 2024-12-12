package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.ServiceDTO;
import com.boraandege.carrental.model.AdditionalService;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.ServiceMapper;
import com.boraandege.carrental.repository.ServiceRepository;
import com.boraandege.carrental.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ServiceDTO addService(ServiceDTO serviceDTO) {
        AdditionalService service = serviceMapper.toEntity(serviceDTO);
        AdditionalService savedService = serviceRepository.save(service);
        return serviceMapper.toDTO(savedService);
    }

    @Override
    public ServiceDTO getServiceById(Long id) {
        AdditionalService service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return serviceMapper.toDTO(service);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        List<AdditionalService> services = serviceRepository.findAll();
        return services.stream()
                .map(serviceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO updateService(Long id, ServiceDTO serviceDTO) {
        AdditionalService existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        existingService.setName(serviceDTO.getName());
        existingService.setPrice(serviceDTO.getPrice());

        AdditionalService updatedService = serviceRepository.save(existingService);
        return serviceMapper.toDTO(updatedService);
    }

    @Override
    public void deleteService(Long id) {
        AdditionalService service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(service);
    }
}
