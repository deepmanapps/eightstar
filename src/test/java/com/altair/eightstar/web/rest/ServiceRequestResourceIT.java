package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.ServiceRequest;
import com.altair.eightstar.domain.enumeration.RQStatus;
import com.altair.eightstar.repository.ServiceRequestRepository;
import com.altair.eightstar.service.dto.ServiceRequestDTO;
import com.altair.eightstar.service.mapper.ServiceRequestMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ServiceRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceRequestResourceIT {

    private static final Instant DEFAULT_REQUEST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUEST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REQUEST_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUEST_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final RQStatus DEFAULT_STATUS_REQUEST = RQStatus.OK;
    private static final RQStatus UPDATED_STATUS_REQUEST = RQStatus.NOK;

    private static final String DEFAULT_REJECTT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTT_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GUEST = false;
    private static final Boolean UPDATED_GUEST = true;

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    private static final Float DEFAULT_TOTAL_PRICE = 1F;
    private static final Float UPDATED_TOTAL_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/service-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestMapper serviceRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceRequestMockMvc;

    private ServiceRequest serviceRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .requestDate(DEFAULT_REQUEST_DATE)
            .requestThruDate(DEFAULT_REQUEST_THRU_DATE)
            .statusRequest(DEFAULT_STATUS_REQUEST)
            .rejecttReason(DEFAULT_REJECTT_REASON)
            .requestDescription(DEFAULT_REQUEST_DESCRIPTION)
            .objectNumber(DEFAULT_OBJECT_NUMBER)
            .guest(DEFAULT_GUEST)
            .quantity(DEFAULT_QUANTITY)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        return serviceRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createUpdatedEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .requestDate(UPDATED_REQUEST_DATE)
            .requestThruDate(UPDATED_REQUEST_THRU_DATE)
            .statusRequest(UPDATED_STATUS_REQUEST)
            .rejecttReason(UPDATED_REJECTT_REASON)
            .requestDescription(UPDATED_REQUEST_DESCRIPTION)
            .objectNumber(UPDATED_OBJECT_NUMBER)
            .guest(UPDATED_GUEST)
            .quantity(UPDATED_QUANTITY)
            .totalPrice(UPDATED_TOTAL_PRICE);
        return serviceRequest;
    }

    @BeforeEach
    public void initTest() {
        serviceRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceRequest() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();
        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);
        restServiceRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testServiceRequest.getRequestThruDate()).isEqualTo(DEFAULT_REQUEST_THRU_DATE);
        assertThat(testServiceRequest.getStatusRequest()).isEqualTo(DEFAULT_STATUS_REQUEST);
        assertThat(testServiceRequest.getRejecttReason()).isEqualTo(DEFAULT_REJECTT_REASON);
        assertThat(testServiceRequest.getRequestDescription()).isEqualTo(DEFAULT_REQUEST_DESCRIPTION);
        assertThat(testServiceRequest.getObjectNumber()).isEqualTo(DEFAULT_OBJECT_NUMBER);
        assertThat(testServiceRequest.getGuest()).isEqualTo(DEFAULT_GUEST);
        assertThat(testServiceRequest.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testServiceRequest.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void createServiceRequestWithExistingId() throws Exception {
        // Create the ServiceRequest with an existing ID
        serviceRequest.setId(1L);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServiceRequests() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList
        restServiceRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestThruDate").value(hasItem(DEFAULT_REQUEST_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusRequest").value(hasItem(DEFAULT_STATUS_REQUEST.toString())))
            .andExpect(jsonPath("$.[*].rejecttReason").value(hasItem(DEFAULT_REJECTT_REASON)))
            .andExpect(jsonPath("$.[*].requestDescription").value(hasItem(DEFAULT_REQUEST_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].objectNumber").value(hasItem(DEFAULT_OBJECT_NUMBER)))
            .andExpect(jsonPath("$.[*].guest").value(hasItem(DEFAULT_GUEST.booleanValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get the serviceRequest
        restServiceRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.requestThruDate").value(DEFAULT_REQUEST_THRU_DATE.toString()))
            .andExpect(jsonPath("$.statusRequest").value(DEFAULT_STATUS_REQUEST.toString()))
            .andExpect(jsonPath("$.rejecttReason").value(DEFAULT_REJECTT_REASON))
            .andExpect(jsonPath("$.requestDescription").value(DEFAULT_REQUEST_DESCRIPTION))
            .andExpect(jsonPath("$.objectNumber").value(DEFAULT_OBJECT_NUMBER))
            .andExpect(jsonPath("$.guest").value(DEFAULT_GUEST.booleanValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingServiceRequest() throws Exception {
        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest
        ServiceRequest updatedServiceRequest = serviceRequestRepository.findById(serviceRequest.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRequest are not directly saved in db
        em.detach(updatedServiceRequest);
        updatedServiceRequest
            .requestDate(UPDATED_REQUEST_DATE)
            .requestThruDate(UPDATED_REQUEST_THRU_DATE)
            .statusRequest(UPDATED_STATUS_REQUEST)
            .rejecttReason(UPDATED_REJECTT_REASON)
            .requestDescription(UPDATED_REQUEST_DESCRIPTION)
            .objectNumber(UPDATED_OBJECT_NUMBER)
            .guest(UPDATED_GUEST)
            .quantity(UPDATED_QUANTITY)
            .totalPrice(UPDATED_TOTAL_PRICE);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(updatedServiceRequest);

        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testServiceRequest.getRequestThruDate()).isEqualTo(UPDATED_REQUEST_THRU_DATE);
        assertThat(testServiceRequest.getStatusRequest()).isEqualTo(UPDATED_STATUS_REQUEST);
        assertThat(testServiceRequest.getRejecttReason()).isEqualTo(UPDATED_REJECTT_REASON);
        assertThat(testServiceRequest.getRequestDescription()).isEqualTo(UPDATED_REQUEST_DESCRIPTION);
        assertThat(testServiceRequest.getObjectNumber()).isEqualTo(UPDATED_OBJECT_NUMBER);
        assertThat(testServiceRequest.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testServiceRequest.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testServiceRequest.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceRequestWithPatch() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest using partial update
        ServiceRequest partialUpdatedServiceRequest = new ServiceRequest();
        partialUpdatedServiceRequest.setId(serviceRequest.getId());

        partialUpdatedServiceRequest
            .objectNumber(UPDATED_OBJECT_NUMBER)
            .guest(UPDATED_GUEST)
            .quantity(UPDATED_QUANTITY)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testServiceRequest.getRequestThruDate()).isEqualTo(DEFAULT_REQUEST_THRU_DATE);
        assertThat(testServiceRequest.getStatusRequest()).isEqualTo(DEFAULT_STATUS_REQUEST);
        assertThat(testServiceRequest.getRejecttReason()).isEqualTo(DEFAULT_REJECTT_REASON);
        assertThat(testServiceRequest.getRequestDescription()).isEqualTo(DEFAULT_REQUEST_DESCRIPTION);
        assertThat(testServiceRequest.getObjectNumber()).isEqualTo(UPDATED_OBJECT_NUMBER);
        assertThat(testServiceRequest.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testServiceRequest.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testServiceRequest.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateServiceRequestWithPatch() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest using partial update
        ServiceRequest partialUpdatedServiceRequest = new ServiceRequest();
        partialUpdatedServiceRequest.setId(serviceRequest.getId());

        partialUpdatedServiceRequest
            .requestDate(UPDATED_REQUEST_DATE)
            .requestThruDate(UPDATED_REQUEST_THRU_DATE)
            .statusRequest(UPDATED_STATUS_REQUEST)
            .rejecttReason(UPDATED_REJECTT_REASON)
            .requestDescription(UPDATED_REQUEST_DESCRIPTION)
            .objectNumber(UPDATED_OBJECT_NUMBER)
            .guest(UPDATED_GUEST)
            .quantity(UPDATED_QUANTITY)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testServiceRequest.getRequestThruDate()).isEqualTo(UPDATED_REQUEST_THRU_DATE);
        assertThat(testServiceRequest.getStatusRequest()).isEqualTo(UPDATED_STATUS_REQUEST);
        assertThat(testServiceRequest.getRejecttReason()).isEqualTo(UPDATED_REJECTT_REASON);
        assertThat(testServiceRequest.getRequestDescription()).isEqualTo(UPDATED_REQUEST_DESCRIPTION);
        assertThat(testServiceRequest.getObjectNumber()).isEqualTo(UPDATED_OBJECT_NUMBER);
        assertThat(testServiceRequest.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testServiceRequest.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testServiceRequest.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();
        serviceRequest.setId(count.incrementAndGet());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeDelete = serviceRequestRepository.findAll().size();

        // Delete the serviceRequest
        restServiceRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
