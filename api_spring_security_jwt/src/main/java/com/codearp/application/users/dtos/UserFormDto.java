package com.codearp.application.users.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","roleNames"}, callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserFormDto extends AuditableDto {

    // person
    private UUID id;

    @NotBlank
    private String name;

    private String surname;

    @Email
    private String email;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    // user account
    @NotBlank
    @Size(min=4,max = 50)
    private String userName;

    @ToString.Exclude
    private String password;

    @Builder.Default
    private Boolean enabled = true;

    // roles
    private String[] roleNames;

}