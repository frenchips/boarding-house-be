package com.adk.kost.repository;


import com.adk.kost.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<Users, UUID> {

    @Query(value = """
                select 
                    username,
                    password,
                    user_type
                from adk_user
                where
                username = :user
                and password = :pass
                and user_type = :type
            """, nativeQuery = true)
    List<Object[]> getUser(@Param("user") String user,
                           @Param("pass") String pass,
                           @Param("type") String type);


    @Query(value = """
                select 
                    user_id
                from adk_user
                where
                username = :user
            """, nativeQuery = true)
    UUID getUser(@Param("user") String user);

    @Query(value = """
                select 
                    username
                from adk_user
                where
                username = :user
            """, nativeQuery = true)
    String getUsername(@Param("user") String user);
}
