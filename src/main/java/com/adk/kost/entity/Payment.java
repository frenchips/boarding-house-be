package com.adk.kost.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "adk_payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment extends BaseEntity{


    @Id
    @Column(name = "INTERNAL_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "INTERNAL_ID")
    @JsonIgnore
    private Order orderId;

//    @Column(name = "PAYMENT_STATUS")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_STATUS", referencedColumnName = "PARAM_CD")
    @JsonIgnore
    private Parameter paymentStatus;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;


    @Column(name = "TOTAL_PAYMENT_AMT")
    private Integer totalPaymentAmt;

}
