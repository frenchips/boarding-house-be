package com.adk.kost.repository;

import com.adk.kost.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    @Query(value = """
                 SELECT 
                    O.INTERNAL_ID 
                FROM ADK_ORDER O 
                JOIN ADK_ROOM R ON O.ORDER_ROOM_ID = R.ROOM_ID
                WHERE R.ROOM_NO = :name
            """, nativeQuery = true)
    UUID getByOrderId(@Param("name") String name);


    @Query(value = """
                 SELECT 
                    O.INTERNAL_ID 
                FROM ADK_ORDER O 
                WHERE O.ORDER_NAME = :name
            """, nativeQuery = true)
    UUID getOrderByName(@Param("name") String name);


    @Query(value = """
                SELECT 
                    ROOM_NO 
                FROM ADK_ORDER O 
                JOIN ADK_ROOM R ON O.ORDER_ROOM_ID = R.ROOM_ID
                WHERE R.ROOM_NO = :name
            """, nativeQuery = true)
    String getRoomNumber(@Param("name") String name);

    @Query(value = """
                select a.* from (
                	  select distinct\s
                       r.room_no,
                       r.room_status,
                       r.price,
                       o.order_status,
                       o.order_name,
                       o.create_at\s
                      from adk_room r\s
                      left join adk_order o on r.room_id = o.order_room_id\s
                         where (:searchValue is null or :searchValue = ''
                      or UPPER(r.room_no) like upper('%' || :searchValue || '%')
                      or UPPER(o.order_status) like upper('%' || :searchValue || '%'))
                ) a
                order by a.order_status = 'NOT_EXIST' desc
            """,nativeQuery = true)
    List<Object[]> findByRoomNoAndOrderStatus(@Param("searchValue") String searchVal);

    @Query(value = """
                select a.* from (
                	  select distinct\s
                       r.room_no,
                       r.room_status,
                       r.price,
                       o.order_status,
                       o.order_name,
                       o.order_date
                      from adk_room r\s
                      join adk_order o on r.room_id = o.order_room_id\s
                         where (:searchValue is null or :searchValue = ''
                      or UPPER(r.room_no) like upper('%' || :searchValue || '%')
                      or UPPER(o.order_status) like upper('%' || :searchValue || '%'))
                ) a
                    order by a.order_status = 'IS_EXIST' DESC
            """,nativeQuery = true)
    List<Object[]> findByRoomNoAndOrder(@Param("searchValue") String searchVal);

    @Query(value = """
                select a.* from (
                	  select distinct\s
                       r.room_no,
                       r.room_status,
                       r.price,
                       o.order_status,
                       o.order_name,
                       o.order_date
                      from adk_room r\s
                      join adk_order o on r.room_id = o.order_room_id\s
                         where (:no is null or :no = ''
                      or UPPER(r.room_no) like upper('%' || :no || '%'))
                ) a
                    order by a.order_status = 'IS_EXIST' DESC
            """,nativeQuery = true)
    List<Object[]> findByRoomNo(@Param("no") String no);

    @Query(value = """
                 select\s
                 	ap.full_name ,
                 	ao.order_date ,
                 	ao.order_status\s
                 from adk_order ao
                 join adk_person ap ON ao.internal_id = ap.person_order_id\s
                 join adk_room ar on ao.order_room_id = ar.room_id\s
                 where ar.room_status = 'IS_EXIST'\s
                 and (:searchValue is null or :searchValue = ''\s
                 or UPPER(ar.room_no) like upper('%' || :searchValue || '%')\s
                 or UPPER(ap.full_name) like upper('%' || :searchValue || '%') )\s
            """,nativeQuery = true)
    List<Object[]> findByOrder(@Param("searchValue") String search);


    @Query(value = """
                select\s
                    ar.room_no,
                    ar.price,
                    ao.order_status,
                    ao.order_date
                from adk_order ao\s
                join adk_room ar on ao.order_room_id = ar.room_id\s
                where  (:searchValue is null or :searchValue = ''\s
                or UPPER(ar.room_no) like upper('%' || :searchValue || '%'))\s
            """,nativeQuery = true)
    List<Object[]> findBySubmission(@Param("searchValue") String search);


}
