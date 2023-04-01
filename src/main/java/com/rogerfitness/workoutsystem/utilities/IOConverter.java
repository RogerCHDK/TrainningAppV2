package com.rogerfitness.workoutsystem.utilities;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IOConverter<D, E> {

    E convertFromDto(D var1);
    D convertFromEntity(E var2);

    default List<E> convertFromDto(Collection<D> dtos){
        return (List)dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    default List<D> convertFromEntity(Collection<E> entities){
        return (List)entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }

}
