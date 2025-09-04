package com.codearp.application.users.dtos;

import com.codearp.application.commons.dtos.AuditableDto;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@EqualsAndHashCode(exclude = {"fullName","id","birthDate"}, callSuper = false)
@Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class PersonDto extends AuditableDto {

    private UUID id;

    private String name;
    private String surname;

    @ToString.Exclude
    private String fullName;

    private String email;

    private LocalDate birthDate;

}
