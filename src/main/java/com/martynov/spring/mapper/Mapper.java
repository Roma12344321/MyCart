package com.martynov.spring.mapper;

import java.util.Collection;
import java.util.List;

public interface Mapper<E, D> {
    E mapDtoToEntity(D dto, Class<E> eClass);

    D mapEntityToDto(E entity, Class<D> dClass);

    List<E> mapDtoListToEntityList(Collection<D> dtoList, Class<E> eClass);

    List<D> mapEntityListToDtoList(Collection<E> entityList, Class<D> dClass);
}
