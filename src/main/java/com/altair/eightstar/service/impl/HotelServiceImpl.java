package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.repository.HotelRepository;
import com.altair.eightstar.service.HotelService;
import com.altair.eightstar.service.dto.HotelDTO;
import com.altair.eightstar.service.mapper.HotelMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hotel}.
 */
@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public HotelDTO save(HotelDTO hotelDTO) {
        log.debug("Request to save Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    @Override
    public HotelDTO update(HotelDTO hotelDTO) {
        log.debug("Request to update Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    @Override
    public Optional<HotelDTO> partialUpdate(HotelDTO hotelDTO) {
        log.debug("Request to partially update Hotel : {}", hotelDTO);

        return hotelRepository
            .findById(hotelDTO.getId())
            .map(existingHotel -> {
                hotelMapper.partialUpdate(existingHotel, hotelDTO);

                return existingHotel;
            })
            .map(hotelRepository::save)
            .map(hotelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDTO> findAll() {
        log.debug("Request to get all Hotels");
        return hotelRepository.findAll().stream().map(hotelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelDTO> findOne(Long id) {
        log.debug("Request to get Hotel : {}", id);
        return hotelRepository.findById(id).map(hotelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
    }
}
