package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.Location;
import com.altair.eightstar.repository.LocationRepository;
import com.altair.eightstar.service.LocationService;
import com.altair.eightstar.service.dto.LocationDTO;
import com.altair.eightstar.service.mapper.LocationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Location}.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public LocationDTO update(LocationDTO locationDTO) {
        log.debug("Request to update Location : {}", locationDTO);
        Location location = locationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public Optional<LocationDTO> partialUpdate(LocationDTO locationDTO) {
        log.debug("Request to partially update Location : {}", locationDTO);

        return locationRepository
            .findById(locationDTO.getId())
            .map(existingLocation -> {
                locationMapper.partialUpdate(existingLocation, locationDTO);

                return existingLocation;
            })
            .map(locationRepository::save)
            .map(locationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable).map(locationMapper::toDto);
    }

    /**
     *  Get all the locations where Hotel is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findAllWhereHotelIsNull() {
        log.debug("Request to get all locations where Hotel is null");
        return StreamSupport
            .stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getHotel() == null)
            .map(locationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the locations where ParkingAll is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findAllWhereParkingAllIsNull() {
        log.debug("Request to get all locations where ParkingAll is null");
        return StreamSupport
            .stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getParkingAll() == null)
            .map(locationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocationDTO> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id).map(locationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
