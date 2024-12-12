package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.ServiceDTO;
import com.boraandege.carrental.model.AdditionalService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceDTO toDTO(AdditionalService service);

    AdditionalService toEntity(ServiceDTO serviceDTO);
}
