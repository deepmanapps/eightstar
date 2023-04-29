package com.altair.eightstar.repository;

import com.altair.eightstar.domain.ProductRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {}
