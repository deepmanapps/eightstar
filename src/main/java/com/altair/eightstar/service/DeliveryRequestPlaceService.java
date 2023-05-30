package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.DeliveryRequestPlace}.
 */
public interface DeliveryRequestPlaceService {
    /**
     * Save a deliveryRequestPlace.
     *
     * @param deliveryRequestPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    DeliveryRequestPlaceDTO save(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO);

    /**
     * Updates a deliveryRequestPlace.
     *
     * @param deliveryRequestPlaceDTO the entity to update.
     * @return the persisted entity.
     */
    DeliveryRequestPlaceDTO update(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO);

    /**
     * Partially updates a deliveryRequestPlace.
     *
     * @param deliveryRequestPlaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeliveryRequestPlaceDTO> partialUpdate(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO);

    /**
     * Get all the deliveryRequestPlaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeliveryRequestPlaceDTO> findAll(Pageable pageable);
    /**
     * Get all the DeliveryRequestPlaceDTO where ServiceRequest is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DeliveryRequestPlaceDTO> findAllWhereServiceRequestIsNull();

    /**
     * Get the "id" deliveryRequestPlace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeliveryRequestPlaceDTO> findOne(Long id);

    /**
     * Delete the "id" deliveryRequestPlace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
