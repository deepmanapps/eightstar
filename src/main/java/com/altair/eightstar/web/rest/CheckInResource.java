package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.CheckInRepository;
import com.altair.eightstar.service.CheckInService;
import com.altair.eightstar.service.dto.CheckInDTO;
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
 * REST controller for managing {@link com.altair.eightstar.domain.CheckIn}.
 */
@RestController
@RequestMapping("/api")
public class CheckInResource {

    private final Logger log = LoggerFactory.getLogger(CheckInResource.class);

    private static final String ENTITY_NAME = "checkIn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckInService checkInService;

    private final CheckInRepository checkInRepository;

    public CheckInResource(CheckInService checkInService, CheckInRepository checkInRepository) {
        this.checkInService = checkInService;
        this.checkInRepository = checkInRepository;
    }

    /**
     * {@code POST  /check-ins} : Create a new checkIn.
     *
     * @param checkInDTO the checkInDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkInDTO, or with status {@code 400 (Bad Request)} if the checkIn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-ins")
    public ResponseEntity<CheckInDTO> createCheckIn(@RequestBody CheckInDTO checkInDTO) throws URISyntaxException {
        log.debug("REST request to save CheckIn : {}", checkInDTO);
        if (checkInDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckInDTO result = checkInService.save(checkInDTO);
        return ResponseEntity
            .created(new URI("/api/check-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-ins/:id} : Updates an existing checkIn.
     *
     * @param id the id of the checkInDTO to save.
     * @param checkInDTO the checkInDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkInDTO,
     * or with status {@code 400 (Bad Request)} if the checkInDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkInDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-ins/{id}")
    public ResponseEntity<CheckInDTO> updateCheckIn(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckInDTO checkInDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckIn : {}, {}", id, checkInDTO);
        if (checkInDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkInDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkInRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckInDTO result = checkInService.update(checkInDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkInDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /check-ins/:id} : Partial updates given fields of an existing checkIn, field will ignore if it is null
     *
     * @param id the id of the checkInDTO to save.
     * @param checkInDTO the checkInDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkInDTO,
     * or with status {@code 400 (Bad Request)} if the checkInDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkInDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkInDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/check-ins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckInDTO> partialUpdateCheckIn(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckInDTO checkInDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckIn partially : {}, {}", id, checkInDTO);
        if (checkInDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkInDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkInRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckInDTO> result = checkInService.partialUpdate(checkInDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkInDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /check-ins} : get all the checkIns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkIns in body.
     */
    @GetMapping("/check-ins")
    public ResponseEntity<List<CheckInDTO>> getAllCheckIns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CheckIns");
        Page<CheckInDTO> page = checkInService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-ins/:id} : get the "id" checkIn.
     *
     * @param id the id of the checkInDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkInDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-ins/{id}")
    public ResponseEntity<CheckInDTO> getCheckIn(@PathVariable Long id) {
        log.debug("REST request to get CheckIn : {}", id);
        Optional<CheckInDTO> checkInDTO = checkInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkInDTO);
    }

    /**
     * {@code DELETE  /check-ins/:id} : delete the "id" checkIn.
     *
     * @param id the id of the checkInDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-ins/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        log.debug("REST request to delete CheckIn : {}", id);
        checkInService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
