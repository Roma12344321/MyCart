package com.martynov.spring.mapper;

import java.util.List;

public interface MapperInterface<E, D> {
    E mapDtoToEntity(D dto, Class<E> eClass);

    D mapEntityToDto(E entity, Class<D> dClass);

    List<E> mapDtoListToEntityList(List<D> dtoList, Class<E> eClass);

    List<D> mapEntityListToDtoList(List<E> entityList, Class<D> dClass);
}
