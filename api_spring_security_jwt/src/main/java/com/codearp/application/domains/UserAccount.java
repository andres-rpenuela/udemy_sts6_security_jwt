package com.codearp.application.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"user","roles"},callSuper = false)
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="USER_ACCOUNTS")
public class UserAccount extends Auditable {

    @Id
    private String userName;

    private String password;

    private Boolean enabled;

    @OneToOne(mappedBy = "userAccount",fetch = FetchType.LAZY)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "USER_ACCOUNTS_ROLES", // tabla intermedia
            joinColumns = @JoinColumn(name = "user_account_id"), // FK hacia UserAcount
            inverseJoinColumns = @JoinColumn(name = "role_id"), // FK hacia Role
            uniqueConstraints = { @UniqueConstraint( columnNames = { "user_account_id", "role_id" }) }
    )
    private List<Role> roles;
}
