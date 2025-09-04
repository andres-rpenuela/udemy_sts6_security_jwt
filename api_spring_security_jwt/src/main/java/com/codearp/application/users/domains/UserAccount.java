package com.codearp.application.users.domains;

import com.codearp.application.commons.domains.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"person","roles"},callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="USER_ACCOUNTS")
public class UserAccount extends Auditable {

    @Id
    private String userName;

    @ToString.Exclude
    private String password;

    private Boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", unique = true)
    private Person person;

    @ManyToMany
    @JoinTable(
            name = "USER_ACCOUNTS_ROLES", // tabla intermedia
            joinColumns = @JoinColumn(name = "user_account_id"), // FK hacia UserAcount
            inverseJoinColumns = @JoinColumn(name = "role_id"), // FK hacia Role
            uniqueConstraints = { @UniqueConstraint( columnNames = { "user_account_id", "role_id" }) }
    )
    private List<Role> roles;
}
