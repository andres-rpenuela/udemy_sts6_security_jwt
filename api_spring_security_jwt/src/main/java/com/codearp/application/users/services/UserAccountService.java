package com.codearp.application.users.services;

import com.codearp.application.users.dtos.UserAccountDto;

public interface UserAccountService {

    UserAccountDto createUserAccount(UserAccountDto userAccountDto);
    UserAccountDto updateUserAccount(String idUserAccount, UserAccountDto userAccountDto);

    boolean isExistedById(UserAccountDto userAccountDto);
    boolean isExistedById(String userName);

}
