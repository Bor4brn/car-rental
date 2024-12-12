package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.LocationDTO;
import com.boraandege.carrental.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO toDTO(Location location);

    Location toEntity(LocationDTO locationDTO);
}

