package com.codearp.application.services;

import com.codearp.application.dtos.PersonDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    List<PersonDto> getAllPerson();
    PersonDto createPerson(PersonDto personDto);
    PersonDto updatePerson(UUID id, PersonDto personDto);
}
