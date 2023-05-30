package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.ParkingAll;
import com.altair.eightstar.repository.ParkingAllRepository;
import com.altair.eightstar.service.ParkingAllService;
import com.altair.eightstar.service.dto.ParkingAllDTO;
import com.altair.eightstar.service.mapper.ParkingAllMapper;
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
 * Service Implementation for managing {@link ParkingAll}.
 */
@Service
@Transactional
public class ParkingAllServiceImpl implements ParkingAllService {

    private final Logger log = LoggerFactory.getLogger(ParkingAllServiceImpl.class);

    private final ParkingAllRepository parkingAllRepository;

    private final ParkingAllMapper parkingAllMapper;

    public ParkingAllServiceImpl(ParkingAllRepository parkingAllRepository, ParkingAllMapper parkingAllMapper) {
        this.parkingAllRepository = parkingAllRepository;
        this.parkingAllMapper = parkingAllMapper;
    }

    @Override
    public ParkingAllDTO save(ParkingAllDTO parkingAllDTO) {
        log.debug("Request to save ParkingAll : {}", parkingAllDTO);
        ParkingAll parkingAll = parkingAllMapper.toEntity(parkingAllDTO);
        parkingAll = parkingAllRepository.save(parkingAll);
        return parkingAllMapper.toDto(parkingAll);
    }

    @Override
    public ParkingAllDTO update(ParkingAllDTO parkingAllDTO) {
        log.debug("Request to update ParkingAll : {}", parkingAllDTO);
        ParkingAll parkingAll = parkingAllMapper.toEntity(parkingAllDTO);
        parkingAll = parkingAllRepository.save(parkingAll);
        return parkingAllMapper.toDto(parkingAll);
    }

    @Override
    public Optional<ParkingAllDTO> partialUpdate(ParkingAllDTO parkingAllDTO) {
        log.debug("Request to partially update ParkingAll : {}", parkingAllDTO);

        return parkingAllRepository
            .findById(parkingAllDTO.getId())
            .map(existingParkingAll -> {
                parkingAllMapper.partialUpdate(existingParkingAll, parkingAllDTO);

                return existingParkingAll;
            })
            .map(parkingAllRepository::save)
            .map(parkingAllMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingAllDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParkingAlls");
        return parkingAllRepository.findAll(pageable).map(parkingAllMapper::toDto);
    }

    /**
     *  Get all the parkingAlls where ServiceRequest is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParkingAllDTO> findAllWhereServiceRequestIsNull() {
        log.debug("Request to get all parkingAlls where ServiceRequest is null");
        return StreamSupport
            .stream(parkingAllRepository.findAll().spliterator(), false)
            .filter(parkingAll -> parkingAll.getServiceRequest() == null)
            .map(parkingAllMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParkingAllDTO> findOne(Long id) {
        log.debug("Request to get ParkingAll : {}", id);
        return parkingAllRepository.findById(id).map(parkingAllMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParkingAll : {}", id);
        parkingAllRepository.deleteById(id);
    }
}
