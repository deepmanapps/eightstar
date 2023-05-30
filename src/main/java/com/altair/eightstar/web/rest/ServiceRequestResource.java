package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.ServiceRequestRepository;
import com.altair.eightstar.service.ServiceRequestService;
import com.altair.eightstar.service.dto.ServiceRequestDTO;
import com.altair.eightstar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.altair.eightstar.domain.ServiceRequest}.
 */
@RestController
@RequestMapping("/api")
public class ServiceRequestResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestResource.class);

    private static final String ENTITY_NAME = "serviceRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRequestService serviceRequestService;

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestResource(ServiceRequestService serviceRequestService, ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestService = serviceRequestService;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * {@code POST  /service-requests} : Create a new serviceRequest.
     *
     * @param serviceRequestDTO the serviceRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRequestDTO, or with status {@code 400 (Bad Request)} if the serviceRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-requests")
    public ResponseEntity<ServiceRequestDTO> createServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save ServiceRequest : {}", serviceRequestDTO);
        if (serviceRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRequestDTO result = serviceRequestService.save(serviceRequestDTO);
        return ResponseEntity
            .created(new URI("/api/service-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-requests/:id} : Updates an existing serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to save.
     * @param serviceRequestDTO the serviceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-requests/{id}")
    public ResponseEntity<ServiceRequestDTO> updateServiceRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceRequestDTO serviceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceRequest : {}, {}", id, serviceRequestDTO);
        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceRequestDTO result = serviceRequestService.update(serviceRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /service-requests/:id} : Partial updates given fields of an existing serviceRequest, field will ignore if it is null
     *
     * @param id the id of the serviceRequestDTO to save.
     * @param serviceRequestDTO the serviceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceRequestDTO> partialUpdateServiceRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceRequestDTO serviceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceRequest partially : {}, {}", id, serviceRequestDTO);
        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceRequestDTO> result = serviceRequestService.partialUpdate(serviceRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-requests} : get all the serviceRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRequests in body.
     */
    @GetMapping("/service-requests")
    public ResponseEntity<List<ServiceRequestDTO>> getAllServiceRequests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ServiceRequests");
        Page<ServiceRequestDTO> page = serviceRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-requests/:id} : get the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-requests/{id}")
    public ResponseEntity<ServiceRequestDTO> getServiceRequest(@PathVariable Long id) {
        log.debug("REST request to get ServiceRequest : {}", id);
        Optional<ServiceRequestDTO> serviceRequestDTO = serviceRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRequestDTO);
    }

    /**
     * {@code DELETE  /service-requests/:id} : delete the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-requests/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRequest : {}", id);
        serviceRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
