package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.EquipmentDTO;
import com.boraandege.carrental.model.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    EquipmentDTO toDTO(Equipment equipment);

    Equipment toEntity(EquipmentDTO equipmentDTO);
}
