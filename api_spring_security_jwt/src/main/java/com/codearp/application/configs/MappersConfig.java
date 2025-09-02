package com.codearp.application.configs;

import com.codearp.application.users.domains.Person;
import com.codearp.application.users.dtos.PersonDto;
import com.codearp.application.users.mappers.UserToUserDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfig {

    @Bean
    public ModelMapper getMapperBean(){
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(Person.class, PersonDto.class).setConverter(context -> new UserToUserDtoConverter().convert(context) );
        //mapper.typeMap(UserFormDto.class, UserAccount.class).setConverter(context -> new UserFormDtoToUserAccount().convert(context) );

        return mapper;
    }
}
