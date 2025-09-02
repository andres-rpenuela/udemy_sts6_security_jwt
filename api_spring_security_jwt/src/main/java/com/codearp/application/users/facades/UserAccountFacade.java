package com.codearp.application.users.facades;

import com.codearp.application.users.dtos.PersonDto;
import com.codearp.application.users.dtos.UserAccountDto;
import com.codearp.application.users.dtos.UserFormDto;

import java.util.List;

public interface UserAccountFacade {

    UserAccountDto createUserAccount(UserFormDto userFormDto);

    UserAccountDto updateUserAccount(String idAccount, UserFormDto userFormDto);

    List<PersonDto> getAllPerson();
}
