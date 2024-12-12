package com.boraandege.carrental.mapper;

import com.boraandege.carrental.dto.MemberDTO;
import com.boraandege.carrental.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "reservations", source = "reservations")
    MemberDTO toDTO(Member member);

    @Mapping(target = "reservations", source = "reservations")
    Member toEntity(MemberDTO memberDTO);
}

