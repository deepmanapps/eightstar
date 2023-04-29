package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.CheckOut;
import com.altair.eightstar.repository.CheckOutRepository;
import com.altair.eightstar.service.CheckOutService;
import com.altair.eightstar.service.dto.CheckOutDTO;
import com.altair.eightstar.service.mapper.CheckOutMapper;
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
 * Service Implementation for managing {@link CheckOut}.
 */
@Service
@Transactional
public class CheckOutServiceImpl implements CheckOutService {

    private final Logger log = LoggerFactory.getLogger(CheckOutServiceImpl.class);

    private final CheckOutRepository checkOutRepository;

    private final CheckOutMapper checkOutMapper;

    public CheckOutServiceImpl(CheckOutRepository checkOutRepository, CheckOutMapper checkOutMapper) {
        this.checkOutRepository = checkOutRepository;
        this.checkOutMapper = checkOutMapper;
    }

    @Override
    public CheckOutDTO save(CheckOutDTO checkOutDTO) {
        log.debug("Request to save CheckOut : {}", checkOutDTO);
        CheckOut checkOut = checkOutMapper.toEntity(checkOutDTO);
        checkOut = checkOutRepository.save(checkOut);
        return checkOutMapper.toDto(checkOut);
    }

    @Override
    public CheckOutDTO update(CheckOutDTO checkOutDTO) {
        log.debug("Request to update CheckOut : {}", checkOutDTO);
        CheckOut checkOut = checkOutMapper.toEntity(checkOutDTO);
        checkOut = checkOutRepository.save(checkOut);
        return checkOutMapper.toDto(checkOut);
    }

    @Override
    public Optional<CheckOutDTO> partialUpdate(CheckOutDTO checkOutDTO) {
        log.debug("Request to partially update CheckOut : {}", checkOutDTO);

        return checkOutRepository
            .findById(checkOutDTO.getId())
            .map(existingCheckOut -> {
                checkOutMapper.partialUpdate(existingCheckOut, checkOutDTO);

                return existingCheckOut;
            })
            .map(checkOutRepository::save)
            .map(checkOutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckOutDTO> findAll() {
        log.debug("Request to get all CheckOuts");
        return checkOutRepository.findAll().stream().map(checkOutMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the checkOuts where CheckIn is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CheckOutDTO> findAllWhereCheckInIsNull() {
        log.debug("Request to get all checkOuts where CheckIn is null");
        return StreamSupport
            .stream(checkOutRepository.findAll().spliterator(), false)
            .filter(checkOut -> checkOut.getCheckIn() == null)
            .map(checkOutMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckOutDTO> findOne(Long id) {
        log.debug("Request to get CheckOut : {}", id);
        return checkOutRepository.findById(id).map(checkOutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckOut : {}", id);
        checkOutRepository.deleteById(id);
    }
}
