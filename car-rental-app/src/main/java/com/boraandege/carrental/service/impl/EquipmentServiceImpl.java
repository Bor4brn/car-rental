package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.EquipmentDTO;
import com.boraandege.carrental.model.Equipment;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.EquipmentMapper;
import com.boraandege.carrental.repository.EquipmentRepository;
import com.boraandege.carrental.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public EquipmentDTO addEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);
        Equipment savedEquipment = equipmentRepository.save(equipment);
        return equipmentMapper.toDTO(savedEquipment);
    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));
        return equipmentMapper.toDTO(equipment);
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return equipments.stream()
                .map(equipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));

        existingEquipment.setName(equipmentDTO.getName());
        existingEquipment.setPrice(equipmentDTO.getPrice());

        Equipment updatedEquipment = equipmentRepository.save(existingEquipment);
        return equipmentMapper.toDTO(updatedEquipment);
    }

    @Override
    public void deleteEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));
        equipmentRepository.delete(equipment);
    }
}
