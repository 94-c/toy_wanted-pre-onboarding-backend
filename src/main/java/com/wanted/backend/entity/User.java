package com.wanted.backend.entity;

import com.wanted.backend.entity.common.DateField;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString(callSuper = true)
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
@Entity
public class User extends DateField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false, length = 100)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Setter
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(String email, String password, String name) {
        return new User(email, password, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() != null && this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name);
    }

}
