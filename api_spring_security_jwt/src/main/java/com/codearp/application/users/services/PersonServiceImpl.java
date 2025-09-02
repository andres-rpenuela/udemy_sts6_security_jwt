package com.codearp.application.users.services;

import com.codearp.application.users.domains.Person;
import com.codearp.application.users.dtos.PersonDto;
import com.codearp.application.users.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> getAllPerson() {
        return personRepository.findAll()
                .stream()
                .map( person -> mapper.map( person, PersonDto.class ) )
                .toList();
    }

    @Override
    @Transactional
    public PersonDto createPerson(@NonNull PersonDto personDto) {
        if(personDto.getId() != null ){
            throw new IllegalStateException("Person with id " + personDto.getId() + " already exists");
        }

        Person person = savePerson(new Person(), personDto);

        return mapper.map( person, PersonDto.class );
    }

    @Override
    public PersonDto updatePerson(UUID id, PersonDto personDto) {
        Person person = Optional.ofNullable( personDto.getId() )
                .flatMap( personRepository::findById )
                .orElseThrow(() -> new IllegalStateException("Person with id " + id + " not found"));

        person = savePerson(person, personDto);

        return mapper.map( person, PersonDto.class );
    }

    private Person savePerson(Person person, PersonDto personDto) {

        person.setName(personDto.getName());
        person.setSurname(personDto.getSurname());
        person.setEmail(personDto.getEmail());
        person.setBirthDate(personDto.getBirthDate());

        personRepository.save(person);
        person = this.personRepository.findById( person.getId() )
                .orElseThrow(() -> new IllegalStateException("Person just saved not found"));

        return person;
    }

}
