package com.adk.kost.repository;

import com.adk.kost.entity.ParameterType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterTypeRepository extends CrudRepository<ParameterType, String> {




    @Query(value = """
                select\s
                	a.*
                from (
                	select distinct on(1)
                 sp.param_type_cd ,
                 sp.param_type_name ,
                 string_agg(spd.param_name  , ', ') as detail_name,
                 sp.create_at\s
                from adk_parameter_type   sp\s
                join adk_parameter spd on sp.param_type_cd  = spd.param_type \s
                where (:searchValue is null or :searchValue = ''
                 or UPPER(sp.param_type_cd) like upper('%' || :searchValue || '%')
                 or UPPER(sp.param_type_name) like upper('%' || :searchValue || '%')
                 or UPPER(spd.param_name) like upper('%' || :searchValue || '%'))
                 GROUP BY sp.param_type_cd\s
                ) a\s
                order by a.create_at asc
            """, nativeQuery = true)
    List<Object[]> findAllParameter(@Param("searchValue") String searchValue);



}
