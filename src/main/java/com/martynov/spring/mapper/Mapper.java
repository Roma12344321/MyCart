package com.martynov.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper<E, D> implements MapperInterface<E, D> {

    private final ModelMapper mapper;

    @Override
    public E mapDtoToEntity(D dto, Class<E> eClass) {
        return mapper.map(dto, eClass);
    }

    @Override
    public D mapEntityToDto(E entity, Class<D> dClass) {
        return mapper.map(entity, dClass);
    }

    @Override
    public List<E> mapDtoListToEntityList(List<D> dtoList, Class<E> eClass) {
        return dtoList.stream()
                .map(dto -> mapDtoToEntity(dto, eClass))
                .toList();
    }

    @Override
    public List<D> mapEntityListToDtoList(List<E> entityList, Class<D> dClass) {
        return entityList.stream()
                .map(e -> mapEntityToDto(e, dClass))
                .toList();
    }
}
