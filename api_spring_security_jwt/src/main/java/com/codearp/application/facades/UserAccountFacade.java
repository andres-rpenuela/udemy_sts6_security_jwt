package com.codearp.application.facades;

import com.codearp.application.dtos.PersonDto;
import com.codearp.application.dtos.UserAccountDto;
import com.codearp.application.dtos.UserFormDto;

import java.util.List;

public interface UserAccountFacade {

    UserAccountDto createUserAccount(UserFormDto userFormDto);

    List<PersonDto> getAllPerson();
}
