package com.adk.kost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "adk_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users extends BaseEntity{

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "NOHP")
    private String noHp;


}
