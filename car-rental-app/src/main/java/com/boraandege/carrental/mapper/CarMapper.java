package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.CarDTO;
import com.boraandege.carrental.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);
}
