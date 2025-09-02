package com.codearp.application.mappers;

import com.codearp.application.configs.MappersConfig;
import com.codearp.application.domains.Role;
import com.codearp.application.domains.Person;
import com.codearp.application.domains.UserAccount;
import com.codearp.application.dtos.RoleDto;
import com.codearp.application.dtos.UserFormDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class PersonDtoMappersTest {

    private final ModelMapper mapper = (new MappersConfig()).getMapperBean();


    @Test
    void givenUser_whenMapper_returnUserFormDto(){

        // GIVEN: un User con UserAccount and Roles
        List<Role> roles = Arrays.asList(
                Role.builder().id(1L).name("ROLE_USER").description("rol basic of application").build(),
                Role.builder().id(1L).name("ROLE_ADMIN").description("rol admin of application").build()
        );

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Andres");
        person.setSurname("Lopez");
        person.setEmail("andres@test.com");
        person.setBirthDate(LocalDate.of(1995, 5, 20));

        UserAccount account = new UserAccount();
        account.setUserName("andres");
        account.setRoles( roles );
        account.setPassword("12345");
        account.setPerson( person );

        // WHEN: lo mapeamos con ModelMapper
        UserFormDto dto = mapper.map(person, UserFormDto.class);

        // THEN: verificamos campos
        Assertions.assertThat(dto.getId()).isEqualTo(person.getId());
        Assertions.assertThat(dto.getName()).isEqualTo( person.getName() );
        Assertions.assertThat(dto.getSurname()).isEqualTo(person.getSurname() );
        Assertions.assertThat(dto.getEmail()).isEqualTo( person.getEmail() );
        Assertions.assertThat(dto.getBirthDate()).isEqualTo( person.getBirthDate() );

        // TODO: arreglar este mapeo
//        Assertions.assertThat(dto.getUserAccountDto()).isNotNull();
//        Assertions.assertThat(dto.getUserAccountDto().getUserName()).isEqualTo( account.getUserName() );
//        Assertions.assertThat(dto.getUserAccountDto().getRoles() )
//                .hasSize( roles.size() )
//                .map( RoleDto::getName )
//                .contains( roles.getFirst().getName() );


    }
}
