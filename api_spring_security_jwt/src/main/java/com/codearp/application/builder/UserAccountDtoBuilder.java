package com.codearp.application.builder;

import com.codearp.application.domains.Person;
import com.codearp.application.domains.Role;
import com.codearp.application.dtos.PersonDto;
import com.codearp.application.dtos.RoleDto;
import com.codearp.application.dtos.UserAccountDto;
import com.codearp.application.dtos.UserFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserAccountDtoBuilder {

    private final UserFormDto userFormDto;

    private UserAccountDto userAccount;

    @Transactional
    public UserAccountDtoBuilder createOrUpdatePerson( ){
        this.userAccount = new UserAccountDto();

        PersonDto personDto = new PersonDto();

        personDto.setId( userFormDto.getId() );
        personDto.setName(userFormDto.getName());
        personDto.setSurname(userFormDto.getSurname());
        personDto.setEmail(userFormDto.getEmail());
        personDto.setBirthDate(userFormDto.getBirthDate());

        this.userAccount.setPerson(personDto);

        return this;
    }

    public UserAccountDtoBuilder assignationRoles(@NonNull List<RoleDto> rolesDto){
        String[] roleNames = userFormDto.getRoleNames();
        String[] roleNamesTemp = Optional.ofNullable(roleNames).orElse(new String[]{});

        if (!Arrays.asList( roleNamesTemp ).contains("ROLE_USER")) {
            roleNamesTemp = Arrays.copyOf(roleNamesTemp, roleNamesTemp.length + 1);
            roleNamesTemp[roleNamesTemp.length - 1] = "ROLE_USER";
        }

        if (rolesDto == null || rolesDto.isEmpty()) {
            throw new IllegalArgumentException("No roles found with the given names");
        }

        String[] finalRoleNamesTemp = roleNamesTemp;
        List<RoleDto> rolesTemp = rolesDto
                .stream()
                .filter(role -> Arrays.asList(finalRoleNamesTemp).contains(role.getName()))
                .collect( Collectors.toList() );

        this.userAccount.setRoles(rolesTemp);

        return this;
    }

    public UserAccountDtoBuilder createOrUpdateUserAccount() {
        if(this.userAccount==null){
            throw new IllegalStateException("Person must be created or updated before creating or updating UserAccount");
        }

        if( this.userAccount.getRoles()==null || this.userAccount.getRoles().isEmpty() || this.userAccount.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_USER")) ){
            throw new IllegalStateException("Roles must be assigned before creating or updating UserAccount");
        }

        if( userFormDto.getPassword() != null){
            // Cambiar password solo si se proporciona uno nuevo
            this.userAccount.setPassword( userFormDto.getPassword() );
        }

        // Mapear propiedades simples
        this.userAccount.setUserName(userFormDto.getUserName());
        this.userAccount.setEnabled(userFormDto.getEnabled());

        return this;
    }

    public UserAccountDto getResult() {
        return this.userAccount;
    }

}
