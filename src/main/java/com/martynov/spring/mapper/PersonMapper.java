package com.martynov.spring.mapper;

import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.entity.Person;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper;
    public Person mapPersonDtoToPerson(PersonDto personDto) {
        return modelMapper.map(personDto,Person.class);
    }
    public PersonDto mapPersonToPersonDto(Person person) {
        return modelMapper.map(person,PersonDto.class);
    }
}
