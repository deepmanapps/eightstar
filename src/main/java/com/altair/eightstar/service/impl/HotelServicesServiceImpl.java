package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.HotelServices;
import com.altair.eightstar.repository.HotelServicesRepository;
import com.altair.eightstar.service.HotelServicesService;
import com.altair.eightstar.service.dto.HotelServicesDTO;
import com.altair.eightstar.service.mapper.HotelServicesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HotelServices}.
 */
@Service
@Transactional
public class HotelServicesServiceImpl implements HotelServicesService {

    private final Logger log = LoggerFactory.getLogger(HotelServicesServiceImpl.class);

    private final HotelServicesRepository hotelServicesRepository;

    private final HotelServicesMapper hotelServicesMapper;

    public HotelServicesServiceImpl(HotelServicesRepository hotelServicesRepository, HotelServicesMapper hotelServicesMapper) {
        this.hotelServicesRepository = hotelServicesRepository;
        this.hotelServicesMapper = hotelServicesMapper;
    }

    @Override
    public HotelServicesDTO save(HotelServicesDTO hotelServicesDTO) {
        log.debug("Request to save HotelServices : {}", hotelServicesDTO);
        HotelServices hotelServices = hotelServicesMapper.toEntity(hotelServicesDTO);
        hotelServices = hotelServicesRepository.save(hotelServices);
        return hotelServicesMapper.toDto(hotelServices);
    }

    @Override
    public HotelServicesDTO update(HotelServicesDTO hotelServicesDTO) {
        log.debug("Request to update HotelServices : {}", hotelServicesDTO);
        HotelServices hotelServices = hotelServicesMapper.toEntity(hotelServicesDTO);
        hotelServices = hotelServicesRepository.save(hotelServices);
        return hotelServicesMapper.toDto(hotelServices);
    }

    @Override
    public Optional<HotelServicesDTO> partialUpdate(HotelServicesDTO hotelServicesDTO) {
        log.debug("Request to partially update HotelServices : {}", hotelServicesDTO);

        return hotelServicesRepository
            .findById(hotelServicesDTO.getId())
            .map(existingHotelServices -> {
                hotelServicesMapper.partialUpdate(existingHotelServices, hotelServicesDTO);

                return existingHotelServices;
            })
            .map(hotelServicesRepository::save)
            .map(hotelServicesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelServicesDTO> findAll() {
        log.debug("Request to get all HotelServices");
        return hotelServicesRepository.findAll().stream().map(hotelServicesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelServicesDTO> findOne(Long id) {
        log.debug("Request to get HotelServices : {}", id);
        return hotelServicesRepository.findById(id).map(hotelServicesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HotelServices : {}", id);
        hotelServicesRepository.deleteById(id);
    }
}
