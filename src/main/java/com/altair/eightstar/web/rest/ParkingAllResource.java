package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.ParkingAllRepository;
import com.altair.eightstar.service.ParkingAllService;
import com.altair.eightstar.service.dto.ParkingAllDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.altair.eightstar.domain.ParkingAll}.
 */
@RestController
@RequestMapping("/api")
public class ParkingAllResource {

    private final Logger log = LoggerFactory.getLogger(ParkingAllResource.class);

    private static final String ENTITY_NAME = "parkingAll";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkingAllService parkingAllService;

    private final ParkingAllRepository parkingAllRepository;

    public ParkingAllResource(ParkingAllService parkingAllService, ParkingAllRepository parkingAllRepository) {
        this.parkingAllService = parkingAllService;
        this.parkingAllRepository = parkingAllRepository;
    }

    /**
     * {@code POST  /parking-alls} : Create a new parkingAll.
     *
     * @param parkingAllDTO the parkingAllDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkingAllDTO, or with status {@code 400 (Bad Request)} if the parkingAll has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parking-alls")
    public ResponseEntity<ParkingAllDTO> createParkingAll(@RequestBody ParkingAllDTO parkingAllDTO) throws URISyntaxException {
        log.debug("REST request to save ParkingAll : {}", parkingAllDTO);
        if (parkingAllDTO.getId() != null) {
            throw new BadRequestAlertException("A new parkingAll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParkingAllDTO result = parkingAllService.save(parkingAllDTO);
        return ResponseEntity
            .created(new URI("/api/parking-alls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parking-alls/:id} : Updates an existing parkingAll.
     *
     * @param id the id of the parkingAllDTO to save.
     * @param parkingAllDTO the parkingAllDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingAllDTO,
     * or with status {@code 400 (Bad Request)} if the parkingAllDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkingAllDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parking-alls/{id}")
    public ResponseEntity<ParkingAllDTO> updateParkingAll(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParkingAllDTO parkingAllDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParkingAll : {}, {}", id, parkingAllDTO);
        if (parkingAllDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingAllDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingAllRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParkingAllDTO result = parkingAllService.update(parkingAllDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingAllDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parking-alls/:id} : Partial updates given fields of an existing parkingAll, field will ignore if it is null
     *
     * @param id the id of the parkingAllDTO to save.
     * @param parkingAllDTO the parkingAllDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingAllDTO,
     * or with status {@code 400 (Bad Request)} if the parkingAllDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parkingAllDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkingAllDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parking-alls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParkingAllDTO> partialUpdateParkingAll(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParkingAllDTO parkingAllDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParkingAll partially : {}, {}", id, parkingAllDTO);
        if (parkingAllDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingAllDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingAllRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParkingAllDTO> result = parkingAllService.partialUpdate(parkingAllDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingAllDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parking-alls} : get all the parkingAlls.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkingAlls in body.
     */
    @GetMapping("/parking-alls")
    public List<ParkingAllDTO> getAllParkingAlls(@RequestParam(required = false) String filter) {
        if ("servicerequest-is-null".equals(filter)) {
            log.debug("REST request to get all ParkingAlls where serviceRequest is null");
            return parkingAllService.findAllWhereServiceRequestIsNull();
        }
        log.debug("REST request to get all ParkingAlls");
        return parkingAllService.findAll();
    }

    /**
     * {@code GET  /parking-alls/:id} : get the "id" parkingAll.
     *
     * @param id the id of the parkingAllDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkingAllDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parking-alls/{id}")
    public ResponseEntity<ParkingAllDTO> getParkingAll(@PathVariable Long id) {
        log.debug("REST request to get ParkingAll : {}", id);
        Optional<ParkingAllDTO> parkingAllDTO = parkingAllService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parkingAllDTO);
    }

    /**
     * {@code DELETE  /parking-alls/:id} : delete the "id" parkingAll.
     *
     * @param id the id of the parkingAllDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parking-alls/{id}")
    public ResponseEntity<Void> deleteParkingAll(@PathVariable Long id) {
        log.debug("REST request to delete ParkingAll : {}", id);
        parkingAllService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
