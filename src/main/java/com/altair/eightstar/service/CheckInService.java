package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.CheckInDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.CheckIn}.
 */
public interface CheckInService {
    /**
     * Save a checkIn.
     *
     * @param checkInDTO the entity to save.
     * @return the persisted entity.
     */
    CheckInDTO save(CheckInDTO checkInDTO);

    /**
     * Updates a checkIn.
     *
     * @param checkInDTO the entity to update.
     * @return the persisted entity.
     */
    CheckInDTO update(CheckInDTO checkInDTO);

    /**
     * Partially updates a checkIn.
     *
     * @param checkInDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckInDTO> partialUpdate(CheckInDTO checkInDTO);

    /**
     * Get all the checkIns.
     *
     * @return the list of entities.
     */
    List<CheckInDTO> findAll();

    /**
     * Get the "id" checkIn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckInDTO> findOne(Long id);

    /**
     * Delete the "id" checkIn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}