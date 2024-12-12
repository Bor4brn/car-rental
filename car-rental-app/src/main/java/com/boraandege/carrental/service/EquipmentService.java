package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.EquipmentDTO;
import java.util.List;

public interface EquipmentService {

    EquipmentDTO addEquipment(EquipmentDTO equipmentDTO);

    EquipmentDTO getEquipmentById(Long id);

    List<EquipmentDTO> getAllEquipments();

    EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO);

    void deleteEquipment(Long id);
}
