package com.adk.kost.repository;

import com.adk.kost.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = """
                SELECT ROOM_ID FROM ADK_ROOM WHERE ROOM_NO = :no
            """, nativeQuery = true)
    Long getById (@Param("no") String no);

    @Query(value = """
                SELECT ROOM_NO FROM ADK_ROOM WHERE ROOM_NO = :no
            """, nativeQuery = true)
    String getRoomNo (@Param("no") String no);


    @Query(value = """
            
                SELECT ROOM_NO, PRICE FROM ADK_ROOM WHERE ROOM_ID = :id
            """, nativeQuery = true)
    List<Object[]> getRoomPrice(@Param("id") Long id);

    @Query(value = """
                SELECT 
                    ROOM_NO,
                    PRICE
                FROM
                    ADK_ROOM
                WHERE (:search is null or :search = '' 
                or UPPER(ROOM_NO) like upper('%' || :search || '%'))
                ORDER BY CREATE_AT DESC
            """, nativeQuery = true)
    List<Object[]> search(@Param("search") String search);
}
