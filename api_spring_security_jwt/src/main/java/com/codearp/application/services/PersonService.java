package com.codearp.application.services;

import com.codearp.application.dtos.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPerson();
    PersonDto createPerson(PersonDto personDto);
    PersonDto updatePerson(Long id, PersonDto personDto);
}
