package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.CheckIn;
import com.altair.eightstar.repository.CheckInRepository;
import com.altair.eightstar.service.CheckInService;
import com.altair.eightstar.service.dto.CheckInDTO;
import com.altair.eightstar.service.mapper.CheckInMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckIn}.
 */
@Service
@Transactional
public class CheckInServiceImpl implements CheckInService {

    private final Logger log = LoggerFactory.getLogger(CheckInServiceImpl.class);

    private final CheckInRepository checkInRepository;

    private final CheckInMapper checkInMapper;

    public CheckInServiceImpl(CheckInRepository checkInRepository, CheckInMapper checkInMapper) {
        this.checkInRepository = checkInRepository;
        this.checkInMapper = checkInMapper;
    }

    @Override
    public CheckInDTO save(CheckInDTO checkInDTO) {
        log.debug("Request to save CheckIn : {}", checkInDTO);
        CheckIn checkIn = checkInMapper.toEntity(checkInDTO);
        checkIn = checkInRepository.save(checkIn);
        return checkInMapper.toDto(checkIn);
    }

    @Override
    public CheckInDTO update(CheckInDTO checkInDTO) {
        log.debug("Request to update CheckIn : {}", checkInDTO);
        CheckIn checkIn = checkInMapper.toEntity(checkInDTO);
        checkIn = checkInRepository.save(checkIn);
        return checkInMapper.toDto(checkIn);
    }

    @Override
    public Optional<CheckInDTO> partialUpdate(CheckInDTO checkInDTO) {
        log.debug("Request to partially update CheckIn : {}", checkInDTO);

        return checkInRepository
            .findById(checkInDTO.getId())
            .map(existingCheckIn -> {
                checkInMapper.partialUpdate(existingCheckIn, checkInDTO);

                return existingCheckIn;
            })
            .map(checkInRepository::save)
            .map(checkInMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckInDTO> findAll() {
        log.debug("Request to get all CheckIns");
        return checkInRepository.findAll().stream().map(checkInMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckInDTO> findOne(Long id) {
        log.debug("Request to get CheckIn : {}", id);
        return checkInRepository.findById(id).map(checkInMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckIn : {}", id);
        checkInRepository.deleteById(id);
    }
}
