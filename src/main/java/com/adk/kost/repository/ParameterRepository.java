package com.adk.kost.repository;


import com.adk.kost.entity.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, String> {

    @Query(value = """
            select\s
            	spd.param_cd ,
            	spd.param_name, \s
            	spd.description
            from adk_parameter   spd\s
            join adk_parameter_type   sp on spd.param_type  = sp.param_type_cd \s
            where spd.param_type  = :param
                
            """, nativeQuery = true)
    List<Object[]> findAllParameterDetail(@Param("param") String searchValue);

    @Query(value = """
                SELECT
                    p.param_cd,
                    p.param_name,
                    p.description
                FROM adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_cd = :code
            """, nativeQuery = true)
    Object[] getByParameterByCode(@Param("code") String code);


    @Query(value = """
                SELECT 
                    p.param_name,
                    p.description
                from adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_type = 'PAY_ORDER'
            """, nativeQuery = true)
    List<Object[]> getAllByParameterByPay();

    @Query(value = """
                SELECT 
                    p.param_name,
                    p.description
                from adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_type = 'AMT_PERSON'
            """, nativeQuery = true)
    List<Object[]> getAllByParameterByAmtPerson();

    @Query(value = """
                SELECT 
                    p.param_name,
                    p.description
                from adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_type = 'GENDER'
            """, nativeQuery = true)
    List<Object[]> getAllByParameterByGender();

    @Query(value = """
                SELECT
                  p.description
                FROM adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_cd = :code
            """, nativeQuery = true)
    String getParamCodeByDescription(@Param("code") String code);


    @Query(value = """
                SELECT
                  p.description
                FROM adk_parameter p
                join adk_parameter_type pt on p.param_type = pt.param_type_cd
                where p.param_cd = 'PAID_NOT_FULL'
            """, nativeQuery = true)
    String getParamCodeByPaidNotFull();


}
