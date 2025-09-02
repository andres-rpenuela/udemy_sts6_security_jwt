package com.codearp.application.mappers;

import com.codearp.application.configs.MappersConfig;
import com.codearp.application.users.domains.Person;
import com.codearp.application.users.dtos.PersonDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

public class PersonFormDtoMappersTest {

    private final ModelMapper mapper = (new MappersConfig()).getMapperBean();

    @Test
    void givenUser_whenMapper_returnUserDto(){

        Person person = Person.builder().id( UUID.randomUUID() )
                .email( "test@email.com")
                .name( "test" )
                .surname( "mapper" )
                .birthDate(LocalDate.of(1992, 6,7))
                .fullName( "test mapper" )
                .build();

        PersonDto personDto = this.mapper.map(person, PersonDto.class );

        Assertions.assertThat(personDto)
                .isNotNull()
                .returns( person.getEmail(), PersonDto::getEmail )
                .returns( person.getBirthDate(), PersonDto::getBirthDate)
                .returns( person.getFullName(), PersonDto::getFullName );

    }
}
