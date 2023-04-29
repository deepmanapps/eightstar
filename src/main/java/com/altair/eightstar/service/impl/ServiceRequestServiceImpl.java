package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.ServiceRequest;
import com.altair.eightstar.repository.ServiceRequestRepository;
import com.altair.eightstar.service.ServiceRequestService;
import com.altair.eightstar.service.dto.ServiceRequestDTO;
import com.altair.eightstar.service.mapper.ServiceRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceRequest}.
 */
@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceRequestMapper serviceRequestMapper) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
    }

    @Override
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return serviceRequestMapper.toDto(serviceRequest);
    }

    @Override
    public ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to update ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return serviceRequestMapper.toDto(serviceRequest);
    }

    @Override
    public Optional<ServiceRequestDTO> partialUpdate(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to partially update ServiceRequest : {}", serviceRequestDTO);

        return serviceRequestRepository
            .findById(serviceRequestDTO.getId())
            .map(existingServiceRequest -> {
                serviceRequestMapper.partialUpdate(existingServiceRequest, serviceRequestDTO);

                return existingServiceRequest;
            })
            .map(serviceRequestRepository::save)
            .map(serviceRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> findAll() {
        log.debug("Request to get all ServiceRequests");
        return serviceRequestRepository
            .findAll()
            .stream()
            .map(serviceRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(Long id) {
        log.debug("Request to get ServiceRequest : {}", id);
        return serviceRequestRepository.findById(id).map(serviceRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceRequest : {}", id);
        serviceRequestRepository.deleteById(id);
    }
}
