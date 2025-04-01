package com.mylab.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "USER_ROLES")
@Getter
@Setter

@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleEntity roleEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @Column(name = "INFO", columnDefinition = "")
    private String info;

}