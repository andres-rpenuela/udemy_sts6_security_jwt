package com.codearp.application.users.dtos;

import com.codearp.application.commons.dtos.AuditableDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"person","roles"},callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserAccountDto extends AuditableDto {

    private String userName;

    private PersonDto person;

    @ToString.Exclude
    private String password;

    private Boolean enabled;

    private List<RoleDto> roles;
}
