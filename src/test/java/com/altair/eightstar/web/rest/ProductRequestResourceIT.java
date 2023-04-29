package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.ProductRequest;
import com.altair.eightstar.repository.ProductRequestRepository;
import com.altair.eightstar.service.dto.ProductRequestDTO;
import com.altair.eightstar.service.mapper.ProductRequestMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductRequestResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PRODUCT_UNIT_PRICE = 1F;
    private static final Float UPDATED_PRODUCT_UNIT_PRICE = 2F;

    private static final Float DEFAULT_PRODUCT_TOTAL_PRICE = 1F;
    private static final Float UPDATED_PRODUCT_TOTAL_PRICE = 2F;

    private static final Float DEFAULT_REQUESTED_QUANTITY = 1F;
    private static final Float UPDATED_REQUESTED_QUANTITY = 2F;

    private static final String ENTITY_API_URL = "/api/product-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRequestRepository productRequestRepository;

    @Autowired
    private ProductRequestMapper productRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductRequestMockMvc;

    private ProductRequest productRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRequest createEntity(EntityManager em) {
        ProductRequest productRequest = new ProductRequest()
            .productName(DEFAULT_PRODUCT_NAME)
            .productUnitPrice(DEFAULT_PRODUCT_UNIT_PRICE)
            .productTotalPrice(DEFAULT_PRODUCT_TOTAL_PRICE)
            .requestedQuantity(DEFAULT_REQUESTED_QUANTITY);
        return productRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRequest createUpdatedEntity(EntityManager em) {
        ProductRequest productRequest = new ProductRequest()
            .productName(UPDATED_PRODUCT_NAME)
            .productUnitPrice(UPDATED_PRODUCT_UNIT_PRICE)
            .productTotalPrice(UPDATED_PRODUCT_TOTAL_PRICE)
            .requestedQuantity(UPDATED_REQUESTED_QUANTITY);
        return productRequest;
    }

    @BeforeEach
    public void initTest() {
        productRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createProductRequest() throws Exception {
        int databaseSizeBeforeCreate = productRequestRepository.findAll().size();
        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);
        restProductRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ProductRequest testProductRequest = productRequestList.get(productRequestList.size() - 1);
        assertThat(testProductRequest.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductRequest.getProductUnitPrice()).isEqualTo(DEFAULT_PRODUCT_UNIT_PRICE);
        assertThat(testProductRequest.getProductTotalPrice()).isEqualTo(DEFAULT_PRODUCT_TOTAL_PRICE);
        assertThat(testProductRequest.getRequestedQuantity()).isEqualTo(DEFAULT_REQUESTED_QUANTITY);
    }

    @Test
    @Transactional
    void createProductRequestWithExistingId() throws Exception {
        // Create the ProductRequest with an existing ID
        productRequest.setId(1L);
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        int databaseSizeBeforeCreate = productRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductRequests() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        // Get all the productRequestList
        restProductRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productUnitPrice").value(hasItem(DEFAULT_PRODUCT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].productTotalPrice").value(hasItem(DEFAULT_PRODUCT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].requestedQuantity").value(hasItem(DEFAULT_REQUESTED_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    void getProductRequest() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        // Get the productRequest
        restProductRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, productRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productRequest.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productUnitPrice").value(DEFAULT_PRODUCT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.productTotalPrice").value(DEFAULT_PRODUCT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.requestedQuantity").value(DEFAULT_REQUESTED_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductRequest() throws Exception {
        // Get the productRequest
        restProductRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductRequest() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();

        // Update the productRequest
        ProductRequest updatedProductRequest = productRequestRepository.findById(productRequest.getId()).get();
        // Disconnect from session so that the updates on updatedProductRequest are not directly saved in db
        em.detach(updatedProductRequest);
        updatedProductRequest
            .productName(UPDATED_PRODUCT_NAME)
            .productUnitPrice(UPDATED_PRODUCT_UNIT_PRICE)
            .productTotalPrice(UPDATED_PRODUCT_TOTAL_PRICE)
            .requestedQuantity(UPDATED_REQUESTED_QUANTITY);
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(updatedProductRequest);

        restProductRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
        ProductRequest testProductRequest = productRequestList.get(productRequestList.size() - 1);
        assertThat(testProductRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductRequest.getProductUnitPrice()).isEqualTo(UPDATED_PRODUCT_UNIT_PRICE);
        assertThat(testProductRequest.getProductTotalPrice()).isEqualTo(UPDATED_PRODUCT_TOTAL_PRICE);
        assertThat(testProductRequest.getRequestedQuantity()).isEqualTo(UPDATED_REQUESTED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductRequestWithPatch() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();

        // Update the productRequest using partial update
        ProductRequest partialUpdatedProductRequest = new ProductRequest();
        partialUpdatedProductRequest.setId(productRequest.getId());

        partialUpdatedProductRequest.productName(UPDATED_PRODUCT_NAME).productUnitPrice(UPDATED_PRODUCT_UNIT_PRICE);

        restProductRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRequest))
            )
            .andExpect(status().isOk());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
        ProductRequest testProductRequest = productRequestList.get(productRequestList.size() - 1);
        assertThat(testProductRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductRequest.getProductUnitPrice()).isEqualTo(UPDATED_PRODUCT_UNIT_PRICE);
        assertThat(testProductRequest.getProductTotalPrice()).isEqualTo(DEFAULT_PRODUCT_TOTAL_PRICE);
        assertThat(testProductRequest.getRequestedQuantity()).isEqualTo(DEFAULT_REQUESTED_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateProductRequestWithPatch() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();

        // Update the productRequest using partial update
        ProductRequest partialUpdatedProductRequest = new ProductRequest();
        partialUpdatedProductRequest.setId(productRequest.getId());

        partialUpdatedProductRequest
            .productName(UPDATED_PRODUCT_NAME)
            .productUnitPrice(UPDATED_PRODUCT_UNIT_PRICE)
            .productTotalPrice(UPDATED_PRODUCT_TOTAL_PRICE)
            .requestedQuantity(UPDATED_REQUESTED_QUANTITY);

        restProductRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRequest))
            )
            .andExpect(status().isOk());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
        ProductRequest testProductRequest = productRequestList.get(productRequestList.size() - 1);
        assertThat(testProductRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductRequest.getProductUnitPrice()).isEqualTo(UPDATED_PRODUCT_UNIT_PRICE);
        assertThat(testProductRequest.getProductTotalPrice()).isEqualTo(UPDATED_PRODUCT_TOTAL_PRICE);
        assertThat(testProductRequest.getRequestedQuantity()).isEqualTo(UPDATED_REQUESTED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductRequest() throws Exception {
        int databaseSizeBeforeUpdate = productRequestRepository.findAll().size();
        productRequest.setId(count.incrementAndGet());

        // Create the ProductRequest
        ProductRequestDTO productRequestDTO = productRequestMapper.toDto(productRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRequest in the database
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductRequest() throws Exception {
        // Initialize the database
        productRequestRepository.saveAndFlush(productRequest);

        int databaseSizeBeforeDelete = productRequestRepository.findAll().size();

        // Delete the productRequest
        restProductRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, productRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductRequest> productRequestList = productRequestRepository.findAll();
        assertThat(productRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
