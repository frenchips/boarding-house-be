package com.adk.kost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adk_parameter")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parameter extends BaseEntity{

    @Id
    @Column(name = "PARAM_CD")
    private String paramCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "PARAM_TYPE", referencedColumnName = "PARAM_TYPE_CD")
    @JsonIgnore
    private ParameterType paramType;

    @Column(name = "PARAM_NAME")
    private String paramName;

    @Column(name = "DESCRIPTION")
    private String description;

}
