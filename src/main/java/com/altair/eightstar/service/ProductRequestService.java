package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.ProductRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.ProductRequest}.
 */
public interface ProductRequestService {
    /**
     * Save a productRequest.
     *
     * @param productRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ProductRequestDTO save(ProductRequestDTO productRequestDTO);

    /**
     * Updates a productRequest.
     *
     * @param productRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ProductRequestDTO update(ProductRequestDTO productRequestDTO);

    /**
     * Partially updates a productRequest.
     *
     * @param productRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductRequestDTO> partialUpdate(ProductRequestDTO productRequestDTO);

    /**
     * Get all the productRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductRequestDTO> findOne(Long id);

    /**
     * Delete the "id" productRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
