package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.CheckOutDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.CheckOut}.
 */
public interface CheckOutService {
    /**
     * Save a checkOut.
     *
     * @param checkOutDTO the entity to save.
     * @return the persisted entity.
     */
    CheckOutDTO save(CheckOutDTO checkOutDTO);

    /**
     * Updates a checkOut.
     *
     * @param checkOutDTO the entity to update.
     * @return the persisted entity.
     */
    CheckOutDTO update(CheckOutDTO checkOutDTO);

    /**
     * Partially updates a checkOut.
     *
     * @param checkOutDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckOutDTO> partialUpdate(CheckOutDTO checkOutDTO);

    /**
     * Get all the checkOuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckOutDTO> findAll(Pageable pageable);
    /**
     * Get all the CheckOutDTO where CheckIn is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CheckOutDTO> findAllWhereCheckInIsNull();

    /**
     * Get the "id" checkOut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckOutDTO> findOne(Long id);

    /**
     * Delete the "id" checkOut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
