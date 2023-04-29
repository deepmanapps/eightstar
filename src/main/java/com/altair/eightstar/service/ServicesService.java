package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.ServicesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.Services}.
 */
public interface ServicesService {
    /**
     * Save a services.
     *
     * @param servicesDTO the entity to save.
     * @return the persisted entity.
     */
    ServicesDTO save(ServicesDTO servicesDTO);

    /**
     * Updates a services.
     *
     * @param servicesDTO the entity to update.
     * @return the persisted entity.
     */
    ServicesDTO update(ServicesDTO servicesDTO);

    /**
     * Partially updates a services.
     *
     * @param servicesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServicesDTO> partialUpdate(ServicesDTO servicesDTO);

    /**
     * Get all the services.
     *
     * @return the list of entities.
     */
    List<ServicesDTO> findAll();

    /**
     * Get the "id" services.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicesDTO> findOne(Long id);

    /**
     * Delete the "id" services.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
