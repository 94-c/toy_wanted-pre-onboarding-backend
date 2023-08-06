package com.wanted.backend.entity;

import com.wanted.backend.entity.common.RoleName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Table(name = "roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Setter
    private RoleName name;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

}
