package com.adk.kost.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "adk_room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private BigInteger roomId;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "price")
    private Integer price;

    @Column(name = "room_status")
    private String roomStatus;

}
