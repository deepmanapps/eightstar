package com.altair.eightstar.repository;

import com.altair.eightstar.domain.ParkingAll;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParkingAll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkingAllRepository extends JpaRepository<ParkingAll, Long> {}
