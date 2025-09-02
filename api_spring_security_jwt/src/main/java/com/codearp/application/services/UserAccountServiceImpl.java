package com.codearp.application.services;

import com.codearp.application.domains.Role;
import com.codearp.application.domains.UserAccount;
import com.codearp.application.dtos.UserAccountDto;
import com.codearp.application.repositories.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService{

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserAccountDto createUserAccount(UserAccountDto userAccountDto) {
        validateThatContainsPerson(userAccountDto);
        validateThatContainsRoles(userAccountDto);

        if( this.isExistedById(userAccountDto) ) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserAccount userAccount = createOrUpdateUserAccount(new UserAccount(), userAccountDto );

        return mapper.map( userAccount, UserAccountDto.class );
    }

    @Override
    @Transactional
    public UserAccountDto updateUserAccount(String idUserAccount, UserAccountDto userAccountDto) {
        UserAccount maybeExistingAccount = userAccountRepository.findById( idUserAccount )
                .orElseThrow( ()-> new IllegalArgumentException("UserAccount with id "+idUserAccount+" does not exist") );

        validateThatContainsPerson(userAccountDto);
        validateThatContainsRoles(userAccountDto);
        validatePersonAssociation(maybeExistingAccount, userAccountDto);

        UserAccount userAccount = createOrUpdateUserAccount( maybeExistingAccount, userAccountDto );

        return mapper.map( userAccount, UserAccountDto.class );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistedById(UserAccountDto userAccountDto) {
        return userAccountRepository.existsById(userAccountDto.getUserName());
    }


    private UserAccount createOrUpdateUserAccount(UserAccount userAccount, UserAccountDto userAccountDto) {

        userAccount.setUserName( userAccountDto.getUserName() );

        if( userAccountDto.getPassword() != null && !userAccountDto.getPassword().isBlank() ){
            String hashedPassword = passwordEncoder.encode( userAccountDto.getPassword() );
            userAccount.setPassword( hashedPassword );
        }
        userAccount.setEnabled( userAccountDto.getEnabled() );

        // Asignar persona
        userAccount.setPerson( mapper.map(userAccountDto.getPerson(), com.codearp.application.domains.Person.class) );

        // Asignar roles
        userAccount.setRoles(
                userAccountDto.getRoles().stream()
                        .map(roleDto -> mapper.map(roleDto, Role.class))
                        .collect(Collectors.toList())
        );

        //userAccount = userAccountRepository.save( userAccount );
        //userAccount = userAccountRepository.findById( userAccount.getUserName() ).orElseThrow(()-> new IllegalStateException("UserAccount was not saved correctly"));
        userAccount = userAccountRepository.saveAndFlush( userAccount );

        return userAccount;
    }

    @Transactional(readOnly = false)
    protected boolean checkPassword(String rawPassword, String hashedPasswordFromDb) {
        return passwordEncoder.matches(rawPassword, hashedPasswordFromDb);
    }

    /** validations **/
    private static void validateThatContainsRoles(UserAccountDto userAccountDto) {
        if( userAccountDto.getRoles() == null || userAccountDto.getRoles().isEmpty()
                || userAccountDto.getRoles().stream().anyMatch(role -> role.getId() == null ) ){
            throw new IllegalArgumentException("At least one role is required to create a User Account");
        }
    }

    private static void validateThatContainsPerson(UserAccountDto userAccountDto) {
        if( userAccountDto.getPerson() == null || userAccountDto.getPerson().getId() == null ){
            throw new IllegalArgumentException("Person information is required to create a User Account");
        }
    }
    /**
     * Valida que si ya existe un usuario, la persona asociada sea la misma
     */
    private void validatePersonAssociation(UserAccount maybeExistingAccount, UserAccountDto userAccountDto) {

        if( maybeExistingAccount.getPerson() != null && userAccountDto.getPerson() != null
                && !maybeExistingAccount.getPerson().getId().equals(userAccountDto.getPerson().getId()) ){
            throw new IllegalArgumentException("The person of user must be the same");
        }

    }

}
