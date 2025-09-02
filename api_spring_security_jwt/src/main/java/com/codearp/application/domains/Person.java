package com.codearp.application.domains;

import jakarta.persistence.*;
import lombok.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@EqualsAndHashCode(exclude = {"fullName","id"}, callSuper = false)
@Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="PERSONS")
public class Person extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    private String name;
    private String surname;

    @ToString.Exclude
    private String fullName;

    @Column(unique = true,nullable = false)
    private String email;

    private LocalDate birthDate;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateFullName() {
        this.fullName = MessageFormat.format("{0} {1}", this.name, this.surname);
    }
}
