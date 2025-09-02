package com.codearp.application.users.mappers;

import com.codearp.application.users.domains.Person;
import com.codearp.application.users.dtos.PersonDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserToUserDtoConverter implements Converter<Person, PersonDto> {


    @Override
    public PersonDto convert(MappingContext<Person, PersonDto> ctx) {
        Person src = ctx.getSource();
        PersonDto dto = new PersonDto();

        dto.setId(src.getId());
        dto.setFullName(src.getFullName());
        dto.setEmail(src.getEmail());
        dto.setBirthDate(src.getBirthDate());

        // audit fields
        dto.setUpdatedAt(src.getUpdatedAt());
        dto.setCreatedAt(src.getCreatedAt());
        dto.setCreatedBy(src.getCreatedBy());
        dto.setUpdatedBy(src.getUpdatedBy());

        return dto;
    }
}
