package com.codearp.application.dtos;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","description"}, callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class RoleDto extends AuditableDto {

    private Long id;

    private String name;

    private String description;

}
