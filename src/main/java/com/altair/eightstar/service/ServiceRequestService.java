package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.ServiceRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.ServiceRequest}.
 */
public interface ServiceRequestService {
    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO);

    /**
     * Updates a serviceRequest.
     *
     * @param serviceRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO);

    /**
     * Partially updates a serviceRequest.
     *
     * @param serviceRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceRequestDTO> partialUpdate(ServiceRequestDTO serviceRequestDTO);

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" serviceRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceRequestDTO> findOne(Long id);

    /**
     * Delete the "id" serviceRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
