package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.DeliveryRequestPlaceRepository;
import com.altair.eightstar.service.DeliveryRequestPlaceService;
import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import com.altair.eightstar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.altair.eightstar.domain.DeliveryRequestPlace}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryRequestPlaceResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryRequestPlaceResource.class);

    private static final String ENTITY_NAME = "deliveryRequestPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryRequestPlaceService deliveryRequestPlaceService;

    private final DeliveryRequestPlaceRepository deliveryRequestPlaceRepository;

    public DeliveryRequestPlaceResource(
        DeliveryRequestPlaceService deliveryRequestPlaceService,
        DeliveryRequestPlaceRepository deliveryRequestPlaceRepository
    ) {
        this.deliveryRequestPlaceService = deliveryRequestPlaceService;
        this.deliveryRequestPlaceRepository = deliveryRequestPlaceRepository;
    }

    /**
     * {@code POST  /delivery-request-places} : Create a new deliveryRequestPlace.
     *
     * @param deliveryRequestPlaceDTO the deliveryRequestPlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryRequestPlaceDTO, or with status {@code 400 (Bad Request)} if the deliveryRequestPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-request-places")
    public ResponseEntity<DeliveryRequestPlaceDTO> createDeliveryRequestPlace(@RequestBody DeliveryRequestPlaceDTO deliveryRequestPlaceDTO)
        throws URISyntaxException {
        log.debug("REST request to save DeliveryRequestPlace : {}", deliveryRequestPlaceDTO);
        if (deliveryRequestPlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryRequestPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryRequestPlaceDTO result = deliveryRequestPlaceService.save(deliveryRequestPlaceDTO);
        return ResponseEntity
            .created(new URI("/api/delivery-request-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-request-places/:id} : Updates an existing deliveryRequestPlace.
     *
     * @param id the id of the deliveryRequestPlaceDTO to save.
     * @param deliveryRequestPlaceDTO the deliveryRequestPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryRequestPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryRequestPlaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryRequestPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-request-places/{id}")
    public ResponseEntity<DeliveryRequestPlaceDTO> updateDeliveryRequestPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeliveryRequestPlaceDTO deliveryRequestPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DeliveryRequestPlace : {}, {}", id, deliveryRequestPlaceDTO);
        if (deliveryRequestPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryRequestPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryRequestPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeliveryRequestPlaceDTO result = deliveryRequestPlaceService.update(deliveryRequestPlaceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryRequestPlaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delivery-request-places/:id} : Partial updates given fields of an existing deliveryRequestPlace, field will ignore if it is null
     *
     * @param id the id of the deliveryRequestPlaceDTO to save.
     * @param deliveryRequestPlaceDTO the deliveryRequestPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryRequestPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryRequestPlaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deliveryRequestPlaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deliveryRequestPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delivery-request-places/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeliveryRequestPlaceDTO> partialUpdateDeliveryRequestPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeliveryRequestPlaceDTO deliveryRequestPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeliveryRequestPlace partially : {}, {}", id, deliveryRequestPlaceDTO);
        if (deliveryRequestPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryRequestPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryRequestPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeliveryRequestPlaceDTO> result = deliveryRequestPlaceService.partialUpdate(deliveryRequestPlaceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryRequestPlaceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delivery-request-places} : get all the deliveryRequestPlaces.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryRequestPlaces in body.
     */
    @GetMapping("/delivery-request-places")
    public ResponseEntity<List<DeliveryRequestPlaceDTO>> getAllDeliveryRequestPlaces(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("servicerequest-is-null".equals(filter)) {
            log.debug("REST request to get all DeliveryRequestPlaces where serviceRequest is null");
            return new ResponseEntity<>(deliveryRequestPlaceService.findAllWhereServiceRequestIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of DeliveryRequestPlaces");
        Page<DeliveryRequestPlaceDTO> page = deliveryRequestPlaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delivery-request-places/:id} : get the "id" deliveryRequestPlace.
     *
     * @param id the id of the deliveryRequestPlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryRequestPlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-request-places/{id}")
    public ResponseEntity<DeliveryRequestPlaceDTO> getDeliveryRequestPlace(@PathVariable Long id) {
        log.debug("REST request to get DeliveryRequestPlace : {}", id);
        Optional<DeliveryRequestPlaceDTO> deliveryRequestPlaceDTO = deliveryRequestPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryRequestPlaceDTO);
    }

    /**
     * {@code DELETE  /delivery-request-places/:id} : delete the "id" deliveryRequestPlace.
     *
     * @param id the id of the deliveryRequestPlaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-request-places/{id}")
    public ResponseEntity<Void> deleteDeliveryRequestPlace(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryRequestPlace : {}", id);
        deliveryRequestPlaceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
