package com.codearp.application.services;

import com.codearp.application.dtos.UserAccountDto;

public interface UserAccountService {

    UserAccountDto createUserAccount(UserAccountDto userAccountDto);
    UserAccountDto updateUserAccount(String idUserAccount, UserAccountDto userAccountDto);

    boolean isExistedById(UserAccountDto userAccountDto);
}
