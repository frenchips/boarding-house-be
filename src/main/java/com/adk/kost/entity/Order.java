package com.adk.kost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ADK_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @Column(name = "INTERNAL_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID internalId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ROOM_ID", referencedColumnName = "ROOM_ID")
    @JsonIgnore
    private Room orderRoomId;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "TOTAL_PERSON")
    private Integer totalPerson;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "NOHP")
    private String noHp;


    //    @Fetch(FetchMode.SUBSELECT)
    @OneToMany( cascade = CascadeType.ALL,  fetch = FetchType.LAZY, mappedBy = "personId")
    @JsonManagedReference
    @JsonIgnore
    private List<Person> personList;

}
