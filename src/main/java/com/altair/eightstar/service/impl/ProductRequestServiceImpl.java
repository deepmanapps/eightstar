package com.altair.eightstar.service.impl;

import com.altair.eightstar.domain.ProductRequest;
import com.altair.eightstar.repository.ProductRequestRepository;
import com.altair.eightstar.service.ProductRequestService;
import com.altair.eightstar.service.dto.ProductRequestDTO;
import com.altair.eightstar.service.mapper.ProductRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductRequest}.
 */
@Service
@Transactional
public class ProductRequestServiceImpl implements ProductRequestService {

    private final Logger log = LoggerFactory.getLogger(ProductRequestServiceImpl.class);

    private final ProductRequestRepository productRequestRepository;

    private final ProductRequestMapper productRequestMapper;

    public ProductRequestServiceImpl(ProductRequestRepository productRequestRepository, ProductRequestMapper productRequestMapper) {
        this.productRequestRepository = productRequestRepository;
        this.productRequestMapper = productRequestMapper;
    }

    @Override
    public ProductRequestDTO save(ProductRequestDTO productRequestDTO) {
        log.debug("Request to save ProductRequest : {}", productRequestDTO);
        ProductRequest productRequest = productRequestMapper.toEntity(productRequestDTO);
        productRequest = productRequestRepository.save(productRequest);
        return productRequestMapper.toDto(productRequest);
    }

    @Override
    public ProductRequestDTO update(ProductRequestDTO productRequestDTO) {
        log.debug("Request to update ProductRequest : {}", productRequestDTO);
        ProductRequest productRequest = productRequestMapper.toEntity(productRequestDTO);
        productRequest = productRequestRepository.save(productRequest);
        return productRequestMapper.toDto(productRequest);
    }

    @Override
    public Optional<ProductRequestDTO> partialUpdate(ProductRequestDTO productRequestDTO) {
        log.debug("Request to partially update ProductRequest : {}", productRequestDTO);

        return productRequestRepository
            .findById(productRequestDTO.getId())
            .map(existingProductRequest -> {
                productRequestMapper.partialUpdate(existingProductRequest, productRequestDTO);

                return existingProductRequest;
            })
            .map(productRequestRepository::save)
            .map(productRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductRequestDTO> findAll() {
        log.debug("Request to get all ProductRequests");
        return productRequestRepository
            .findAll()
            .stream()
            .map(productRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductRequestDTO> findOne(Long id) {
        log.debug("Request to get ProductRequest : {}", id);
        return productRequestRepository.findById(id).map(productRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductRequest : {}", id);
        productRequestRepository.deleteById(id);
    }
}
