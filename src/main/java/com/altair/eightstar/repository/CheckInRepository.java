package com.altair.eightstar.repository;

import com.altair.eightstar.domain.CheckIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {}
