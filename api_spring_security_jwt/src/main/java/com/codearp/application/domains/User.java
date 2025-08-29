package com.codearp.application.domains;

import jakarta.persistence.*;
import lombok.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@EqualsAndHashCode(exclude = {"fullName","id","userAccount"}, callSuper = false)
@Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="USERS")
public class User extends Auditable {

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_account_id")
    @ToString.Exclude
    private UserAccount userAccount;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateFullName() {
        this.fullName = MessageFormat.format("{0} {1}", this.name, this.surname);
    }
}
