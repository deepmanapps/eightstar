package com.altair.eightstar.repository;

import com.altair.eightstar.domain.DeliveryRequestPlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DeliveryRequestPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRequestPlaceRepository extends JpaRepository<DeliveryRequestPlace, Long> {}
