package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.ServiceDTO;
import java.util.List;

public interface ServiceService {

    ServiceDTO addService(ServiceDTO serviceDTO);

    ServiceDTO getServiceById(Long id);

    List<ServiceDTO> getAllServices();

    ServiceDTO updateService(Long id, ServiceDTO serviceDTO);

    void deleteService(Long id);
}
