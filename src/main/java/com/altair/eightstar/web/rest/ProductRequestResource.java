package com.altair.eightstar.web.rest;

import com.altair.eightstar.repository.ProductRequestRepository;
import com.altair.eightstar.service.ProductRequestService;
import com.altair.eightstar.service.dto.ProductRequestDTO;
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
 * REST controller for managing {@link com.altair.eightstar.domain.ProductRequest}.
 */
@RestController
@RequestMapping("/api")
public class ProductRequestResource {

    private final Logger log = LoggerFactory.getLogger(ProductRequestResource.class);

    private static final String ENTITY_NAME = "productRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductRequestService productRequestService;

    private final ProductRequestRepository productRequestRepository;

    public ProductRequestResource(ProductRequestService productRequestService, ProductRequestRepository productRequestRepository) {
        this.productRequestService = productRequestService;
        this.productRequestRepository = productRequestRepository;
    }

    /**
     * {@code POST  /product-requests} : Create a new productRequest.
     *
     * @param productRequestDTO the productRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productRequestDTO, or with status {@code 400 (Bad Request)} if the productRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-requests")
    public ResponseEntity<ProductRequestDTO> createProductRequest(@RequestBody ProductRequestDTO productRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductRequest : {}", productRequestDTO);
        if (productRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new productRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductRequestDTO result = productRequestService.save(productRequestDTO);
        return ResponseEntity
            .created(new URI("/api/product-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-requests/:id} : Updates an existing productRequest.
     *
     * @param id the id of the productRequestDTO to save.
     * @param productRequestDTO the productRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRequestDTO,
     * or with status {@code 400 (Bad Request)} if the productRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-requests/{id}")
    public ResponseEntity<ProductRequestDTO> updateProductRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductRequestDTO productRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductRequest : {}, {}", id, productRequestDTO);
        if (productRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductRequestDTO result = productRequestService.update(productRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-requests/:id} : Partial updates given fields of an existing productRequest, field will ignore if it is null
     *
     * @param id the id of the productRequestDTO to save.
     * @param productRequestDTO the productRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRequestDTO,
     * or with status {@code 400 (Bad Request)} if the productRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductRequestDTO> partialUpdateProductRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductRequestDTO productRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductRequest partially : {}, {}", id, productRequestDTO);
        if (productRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductRequestDTO> result = productRequestService.partialUpdate(productRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-requests} : get all the productRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productRequests in body.
     */
    @GetMapping("/product-requests")
    public List<ProductRequestDTO> getAllProductRequests() {
        log.debug("REST request to get all ProductRequests");
        return productRequestService.findAll();
    }

    /**
     * {@code GET  /product-requests/:id} : get the "id" productRequest.
     *
     * @param id the id of the productRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-requests/{id}")
    public ResponseEntity<ProductRequestDTO> getProductRequest(@PathVariable Long id) {
        log.debug("REST request to get ProductRequest : {}", id);
        Optional<ProductRequestDTO> productRequestDTO = productRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productRequestDTO);
    }

    /**
     * {@code DELETE  /product-requests/:id} : delete the "id" productRequest.
     *
     * @param id the id of the productRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-requests/{id}")
    public ResponseEntity<Void> deleteProductRequest(@PathVariable Long id) {
        log.debug("REST request to delete ProductRequest : {}", id);
        productRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
