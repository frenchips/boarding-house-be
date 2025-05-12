package com.adk.kost.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "ADK_PERSON")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseEntity {
    @Id
    @Column(name = "PERSON_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID personId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "nik")
    private String nik;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PERSON_ORDER_ID", referencedColumnName = "INTERNAL_ID")
    @JsonBackReference
    @JsonIgnore
    private Order orderId;
}
