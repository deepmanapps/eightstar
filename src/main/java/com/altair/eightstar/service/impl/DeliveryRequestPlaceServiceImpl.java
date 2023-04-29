package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.DeliveryRequestPlace;
import com.altair.eightstar.repository.DeliveryRequestPlaceRepository;
import com.altair.eightstar.service.DeliveryRequestPlaceService;
import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import com.altair.eightstar.service.mapper.DeliveryRequestPlaceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeliveryRequestPlace}.
 */
@Service
@Transactional
public class DeliveryRequestPlaceServiceImpl implements DeliveryRequestPlaceService {

    private final Logger log = LoggerFactory.getLogger(DeliveryRequestPlaceServiceImpl.class);

    private final DeliveryRequestPlaceRepository deliveryRequestPlaceRepository;

    private final DeliveryRequestPlaceMapper deliveryRequestPlaceMapper;

    public DeliveryRequestPlaceServiceImpl(
        DeliveryRequestPlaceRepository deliveryRequestPlaceRepository,
        DeliveryRequestPlaceMapper deliveryRequestPlaceMapper
    ) {
        this.deliveryRequestPlaceRepository = deliveryRequestPlaceRepository;
        this.deliveryRequestPlaceMapper = deliveryRequestPlaceMapper;
    }

    @Override
    public DeliveryRequestPlaceDTO save(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO) {
        log.debug("Request to save DeliveryRequestPlace : {}", deliveryRequestPlaceDTO);
        DeliveryRequestPlace deliveryRequestPlace = deliveryRequestPlaceMapper.toEntity(deliveryRequestPlaceDTO);
        deliveryRequestPlace = deliveryRequestPlaceRepository.save(deliveryRequestPlace);
        return deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);
    }

    @Override
    public DeliveryRequestPlaceDTO update(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO) {
        log.debug("Request to update DeliveryRequestPlace : {}", deliveryRequestPlaceDTO);
        DeliveryRequestPlace deliveryRequestPlace = deliveryRequestPlaceMapper.toEntity(deliveryRequestPlaceDTO);
        deliveryRequestPlace = deliveryRequestPlaceRepository.save(deliveryRequestPlace);
        return deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);
    }

    @Override
    public Optional<DeliveryRequestPlaceDTO> partialUpdate(DeliveryRequestPlaceDTO deliveryRequestPlaceDTO) {
        log.debug("Request to partially update DeliveryRequestPlace : {}", deliveryRequestPlaceDTO);

        return deliveryRequestPlaceRepository
            .findById(deliveryRequestPlaceDTO.getId())
            .map(existingDeliveryRequestPlace -> {
                deliveryRequestPlaceMapper.partialUpdate(existingDeliveryRequestPlace, deliveryRequestPlaceDTO);

                return existingDeliveryRequestPlace;
            })
            .map(deliveryRequestPlaceRepository::save)
            .map(deliveryRequestPlaceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryRequestPlaceDTO> findAll() {
        log.debug("Request to get all DeliveryRequestPlaces");
        return deliveryRequestPlaceRepository
            .findAll()
            .stream()
            .map(deliveryRequestPlaceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the deliveryRequestPlaces where ServiceRequest is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryRequestPlaceDTO> findAllWhereServiceRequestIsNull() {
        log.debug("Request to get all deliveryRequestPlaces where ServiceRequest is null");
        return StreamSupport
            .stream(deliveryRequestPlaceRepository.findAll().spliterator(), false)
            .filter(deliveryRequestPlace -> deliveryRequestPlace.getServiceRequest() == null)
            .map(deliveryRequestPlaceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryRequestPlaceDTO> findOne(Long id) {
        log.debug("Request to get DeliveryRequestPlace : {}", id);
        return deliveryRequestPlaceRepository.findById(id).map(deliveryRequestPlaceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryRequestPlace : {}", id);
        deliveryRequestPlaceRepository.deleteById(id);
    }
}
