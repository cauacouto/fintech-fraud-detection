package com.example.account.service.Mapper;

import com.example.account.service.domin.Conta;
import com.example.account.service.dto.ContaDto;
import com.example.account.service.dto.ContaDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContaMapper {


    Conta toEntity(ContaDto dto);
    @Mapping(source = "id", target = "id")
    ContaDtoResponse toDto(Conta conta);
}
