package com.altair.eightstar.repository;

import com.altair.eightstar.domain.CheckIn;
import com.altair.eightstar.service.dto.CheckInDTO;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    @Query(
        value = "select * from check_in ch\n" +
        " inner join hotel h on h.id=ch.hotel_id\n" +
        " inner join es_user u on (h.user_id=u.id and u.login=:id)",
        nativeQuery = true
    )
    Page<CheckIn> getCheckInsWithUser(@Param("id") String id, Pageable pageable);
}
