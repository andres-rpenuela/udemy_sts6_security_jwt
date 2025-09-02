package com.codearp.application.users.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","roles"}, callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserFormDto extends AuditableDto {

    // person
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;

    // user account
    private String userName;
    @ToString.Exclude
    private String password;
    private Boolean enabled;

    // roles
    private String[] roleNames;

}