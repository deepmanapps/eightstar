package com.altair.eightstar.repository;

import com.altair.eightstar.domain.HotelServices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HotelServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelServicesRepository extends JpaRepository<HotelServices, Long> {}
