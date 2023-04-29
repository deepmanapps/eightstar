package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.ParkingAllDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.ParkingAll}.
 */
public interface ParkingAllService {
    /**
     * Save a parkingAll.
     *
     * @param parkingAllDTO the entity to save.
     * @return the persisted entity.
     */
    ParkingAllDTO save(ParkingAllDTO parkingAllDTO);

    /**
     * Updates a parkingAll.
     *
     * @param parkingAllDTO the entity to update.
     * @return the persisted entity.
     */
    ParkingAllDTO update(ParkingAllDTO parkingAllDTO);

    /**
     * Partially updates a parkingAll.
     *
     * @param parkingAllDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParkingAllDTO> partialUpdate(ParkingAllDTO parkingAllDTO);

    /**
     * Get all the parkingAlls.
     *
     * @return the list of entities.
     */
    List<ParkingAllDTO> findAll();
    /**
     * Get all the ParkingAllDTO where ServiceRequest is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ParkingAllDTO> findAllWhereServiceRequestIsNull();

    /**
     * Get the "id" parkingAll.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParkingAllDTO> findOne(Long id);

    /**
     * Delete the "id" parkingAll.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
