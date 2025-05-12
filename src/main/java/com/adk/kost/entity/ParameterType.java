package com.adk.kost.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "adk_parameter_type")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ParameterType extends BaseEntity{

    @Id
    @Column(name = "PARAM_TYPE_CD")
    private String paramTypeCd;

    @Column(name = "PARAM_TYPE_NAME")
    private String paramTypeName;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany( cascade = CascadeType.ALL,  fetch = FetchType.LAZY, mappedBy = "paramType", orphanRemoval = true)
//    @JsonManagedReference
    private List<Parameter> parameterList;


}
