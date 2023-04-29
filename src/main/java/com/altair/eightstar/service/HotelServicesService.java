package com.altair.eightstar.service;

import com.altair.eightstar.service.dto.HotelServicesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.altair.eightstar.domain.HotelServices}.
 */
public interface HotelServicesService {
    /**
     * Save a hotelServices.
     *
     * @param hotelServicesDTO the entity to save.
     * @return the persisted entity.
     */
    HotelServicesDTO save(HotelServicesDTO hotelServicesDTO);

    /**
     * Updates a hotelServices.
     *
     * @param hotelServicesDTO the entity to update.
     * @return the persisted entity.
     */
    HotelServicesDTO update(HotelServicesDTO hotelServicesDTO);

    /**
     * Partially updates a hotelServices.
     *
     * @param hotelServicesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HotelServicesDTO> partialUpdate(HotelServicesDTO hotelServicesDTO);

    /**
     * Get all the hotelServices.
     *
     * @return the list of entities.
     */
    List<HotelServicesDTO> findAll();

    /**
     * Get the "id" hotelServices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HotelServicesDTO> findOne(Long id);

    /**
     * Delete the "id" hotelServices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
