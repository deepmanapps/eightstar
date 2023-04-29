package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.ProductRequestDTO;
import java.util.List;
import java.util.Optional;

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
     * @return the list of entities.
     */
    List<ProductRequestDTO> findAll();

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
