package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.HotelDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.Hotel}.
 */
public interface HotelService {
    /**
     * Save a hotel.
     *
     * @param hotelDTO the entity to save.
     * @return the persisted entity.
     */
    HotelDTO save(HotelDTO hotelDTO);

    /**
     * Updates a hotel.
     *
     * @param hotelDTO the entity to update.
     * @return the persisted entity.
     */
    HotelDTO update(HotelDTO hotelDTO);

    /**
     * Partially updates a hotel.
     *
     * @param hotelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HotelDTO> partialUpdate(HotelDTO hotelDTO);

    /**
     * Get all the hotels.
     *
     * @return the list of entities.
     */
    List<HotelDTO> findAll();

    /**
     * Get the "id" hotel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HotelDTO> findOne(Long id);

    /**
     * Delete the "id" hotel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
