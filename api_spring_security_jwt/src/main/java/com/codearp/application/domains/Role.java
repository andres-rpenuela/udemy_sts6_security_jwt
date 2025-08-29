package com.codearp.application.domains;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","description","userAccounts"}, callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="ROLES")
public class Role extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Lob
    private String description;

}
