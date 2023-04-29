package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.HotelServicesRepository;
import com.altair.eightstar.service.HotelServicesService;
import com.altair.eightstar.service.dto.HotelServicesDTO;
import com.altair.eightstar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.altair.eightstar.domain.HotelServices}.
 */
@RestController
@RequestMapping("/api")
public class HotelServicesResource {

    private final Logger log = LoggerFactory.getLogger(HotelServicesResource.class);

    private static final String ENTITY_NAME = "hotelServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelServicesService hotelServicesService;

    private final HotelServicesRepository hotelServicesRepository;

    public HotelServicesResource(HotelServicesService hotelServicesService, HotelServicesRepository hotelServicesRepository) {
        this.hotelServicesService = hotelServicesService;
        this.hotelServicesRepository = hotelServicesRepository;
    }

    /**
     * {@code POST  /hotel-services} : Create a new hotelServices.
     *
     * @param hotelServicesDTO the hotelServicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelServicesDTO, or with status {@code 400 (Bad Request)} if the hotelServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-services")
    public ResponseEntity<HotelServicesDTO> createHotelServices(@RequestBody HotelServicesDTO hotelServicesDTO) throws URISyntaxException {
        log.debug("REST request to save HotelServices : {}", hotelServicesDTO);
        if (hotelServicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelServicesDTO result = hotelServicesService.save(hotelServicesDTO);
        return ResponseEntity
            .created(new URI("/api/hotel-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-services/:id} : Updates an existing hotelServices.
     *
     * @param id the id of the hotelServicesDTO to save.
     * @param hotelServicesDTO the hotelServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelServicesDTO,
     * or with status {@code 400 (Bad Request)} if the hotelServicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-services/{id}")
    public ResponseEntity<HotelServicesDTO> updateHotelServices(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelServicesDTO hotelServicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelServices : {}, {}", id, hotelServicesDTO);
        if (hotelServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelServicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelServicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HotelServicesDTO result = hotelServicesService.update(hotelServicesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelServicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotel-services/:id} : Partial updates given fields of an existing hotelServices, field will ignore if it is null
     *
     * @param id the id of the hotelServicesDTO to save.
     * @param hotelServicesDTO the hotelServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelServicesDTO,
     * or with status {@code 400 (Bad Request)} if the hotelServicesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelServicesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotel-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelServicesDTO> partialUpdateHotelServices(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelServicesDTO hotelServicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelServices partially : {}, {}", id, hotelServicesDTO);
        if (hotelServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelServicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelServicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelServicesDTO> result = hotelServicesService.partialUpdate(hotelServicesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelServicesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-services} : get all the hotelServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelServices in body.
     */
    @GetMapping("/hotel-services")
    public List<HotelServicesDTO> getAllHotelServices() {
        log.debug("REST request to get all HotelServices");
        return hotelServicesService.findAll();
    }

    /**
     * {@code GET  /hotel-services/:id} : get the "id" hotelServices.
     *
     * @param id the id of the hotelServicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelServicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-services/{id}")
    public ResponseEntity<HotelServicesDTO> getHotelServices(@PathVariable Long id) {
        log.debug("REST request to get HotelServices : {}", id);
        Optional<HotelServicesDTO> hotelServicesDTO = hotelServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelServicesDTO);
    }

    /**
     * {@code DELETE  /hotel-services/:id} : delete the "id" hotelServices.
     *
     * @param id the id of the hotelServicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-services/{id}")
    public ResponseEntity<Void> deleteHotelServices(@PathVariable Long id) {
        log.debug("REST request to delete HotelServices : {}", id);
        hotelServicesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
