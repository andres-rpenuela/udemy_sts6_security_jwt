package com.codearp.application.facades;

import com.codearp.application.builder.UserAccountDtoBuilder;
import com.codearp.application.dtos.PersonDto;
import com.codearp.application.dtos.RoleDto;
import com.codearp.application.dtos.UserAccountDto;
import com.codearp.application.dtos.UserFormDto;
import com.codearp.application.services.PersonService;
import com.codearp.application.services.RoleService;
import com.codearp.application.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountFacadeImpl implements UserAccountFacade {

    private final PersonService personService;
    private final RoleService roleService;
    private final UserAccountService userAccountService;

    @Override
    @Transactional
    public UserAccountDto createUserAccount(UserFormDto userFormDto) {
        List<RoleDto> rolesDto = Optional.ofNullable(roleService.findInNames(userFormDto.getRoleNames()))
                .filter(list -> !list.isEmpty())
                .orElseGet(() -> roleService.findInNames(new String[]{"ROLE_USER"}));


        UserAccountDtoBuilder userAccountDtoBuilder = new UserAccountDtoBuilder(userFormDto);
        UserAccountDto userAccountDto = userAccountDtoBuilder.createOrUpdatePerson()
                .assignationRoles(rolesDto)
                .createOrUpdateUserAccount()
                .getResult();

        PersonDto personDto = personService.createPerson(userAccountDto.getPerson());
        userAccountDto.setPerson( personDto );

        return userAccountService.createUserAccount(userAccountDto);
    }

    @Override
    @Transactional
    public UserAccountDto updateUserAccount(String idAccount, UserFormDto userFormDto) {
        // que puede modificar: datos de la cuenta, datos de la persona y/o roles
        // no puede modificar: username
        if (idAccount==null || userFormDto == null ||userFormDto.getUserName() == null || !userFormDto.getUserName().equalsIgnoreCase(idAccount)){
            throw new IllegalArgumentException("User name not be modified");
        }

        List<RoleDto> rolesDto = Optional.ofNullable(roleService.findInNames(userFormDto.getRoleNames()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("The roles is required"));

        UserAccountDtoBuilder userAccountDtoBuilder = new UserAccountDtoBuilder(userFormDto);
        UserAccountDto userAccountDto = userAccountDtoBuilder.createOrUpdatePerson()
                .assignationRoles(rolesDto)
                .createOrUpdateUserAccount()
                .getResult();

        if( userAccountDto.getPerson().getId() != null ){
            PersonDto personDto = personService.updatePerson(userAccountDto.getPerson().getId(), userAccountDto.getPerson());
            userAccountDto.setPerson( personDto );
        }

        return userAccountService.updateUserAccount(idAccount, userAccountDto);

    }

    @Override
    public List<PersonDto> getAllPerson() {
        return personService.getAllPerson();
    }
}
