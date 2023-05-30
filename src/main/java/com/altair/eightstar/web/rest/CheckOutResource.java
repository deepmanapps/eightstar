package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.CheckOutRepository;
import com.altair.eightstar.service.CheckOutService;
import com.altair.eightstar.service.dto.CheckOutDTO;
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
 * REST controller for managing {@link com.altair.eightstar.domain.CheckOut}.
 */
@RestController
@RequestMapping("/api")
public class CheckOutResource {

    private final Logger log = LoggerFactory.getLogger(CheckOutResource.class);

    private static final String ENTITY_NAME = "checkOut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckOutService checkOutService;

    private final CheckOutRepository checkOutRepository;

    public CheckOutResource(CheckOutService checkOutService, CheckOutRepository checkOutRepository) {
        this.checkOutService = checkOutService;
        this.checkOutRepository = checkOutRepository;
    }

    /**
     * {@code POST  /check-outs} : Create a new checkOut.
     *
     * @param checkOutDTO the checkOutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkOutDTO, or with status {@code 400 (Bad Request)} if the checkOut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-outs")
    public ResponseEntity<CheckOutDTO> createCheckOut(@RequestBody CheckOutDTO checkOutDTO) throws URISyntaxException {
        log.debug("REST request to save CheckOut : {}", checkOutDTO);
        if (checkOutDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckOutDTO result = checkOutService.save(checkOutDTO);
        return ResponseEntity
            .created(new URI("/api/check-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-outs/:id} : Updates an existing checkOut.
     *
     * @param id the id of the checkOutDTO to save.
     * @param checkOutDTO the checkOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkOutDTO,
     * or with status {@code 400 (Bad Request)} if the checkOutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-outs/{id}")
    public ResponseEntity<CheckOutDTO> updateCheckOut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckOutDTO checkOutDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckOut : {}, {}", id, checkOutDTO);
        if (checkOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkOutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkOutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckOutDTO result = checkOutService.update(checkOutDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkOutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /check-outs/:id} : Partial updates given fields of an existing checkOut, field will ignore if it is null
     *
     * @param id the id of the checkOutDTO to save.
     * @param checkOutDTO the checkOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkOutDTO,
     * or with status {@code 400 (Bad Request)} if the checkOutDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkOutDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/check-outs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckOutDTO> partialUpdateCheckOut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckOutDTO checkOutDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CheckOut partially : {}, {}", id, checkOutDTO);
        if (checkOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkOutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkOutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckOutDTO> result = checkOutService.partialUpdate(checkOutDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkOutDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /check-outs} : get all the checkOuts.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkOuts in body.
     */
    @GetMapping("/check-outs")
    public ResponseEntity<List<CheckOutDTO>> getAllCheckOuts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("checkin-is-null".equals(filter)) {
            log.debug("REST request to get all CheckOuts where checkIn is null");
            return new ResponseEntity<>(checkOutService.findAllWhereCheckInIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of CheckOuts");
        Page<CheckOutDTO> page = checkOutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-outs/:id} : get the "id" checkOut.
     *
     * @param id the id of the checkOutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkOutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-outs/{id}")
    public ResponseEntity<CheckOutDTO> getCheckOut(@PathVariable Long id) {
        log.debug("REST request to get CheckOut : {}", id);
        Optional<CheckOutDTO> checkOutDTO = checkOutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkOutDTO);
    }

    /**
     * {@code DELETE  /check-outs/:id} : delete the "id" checkOut.
     *
     * @param id the id of the checkOutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-outs/{id}")
    public ResponseEntity<Void> deleteCheckOut(@PathVariable Long id) {
        log.debug("REST request to delete CheckOut : {}", id);
        checkOutService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
