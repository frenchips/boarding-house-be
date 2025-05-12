package com.adk.kost.repository;

import com.adk.kost.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> {

    @Query(value = """
                select
                 	a.internal_id
                 from adk.adk_payment a
                 join adk.adk_order o on a.order_id  = o.internal_id
                 where o.order_name = :name
            """,nativeQuery = true)
    UUID getPaymentByName(@Param("name") String name);
}
