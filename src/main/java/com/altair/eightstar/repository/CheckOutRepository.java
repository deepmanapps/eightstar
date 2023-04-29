package com.altair.eightstar.repository;

import com.altair.eightstar.domain.CheckOut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {}
