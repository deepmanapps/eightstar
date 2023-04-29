package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.DeliveryRequestPlace;
import com.altair.eightstar.repository.DeliveryRequestPlaceRepository;
import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import com.altair.eightstar.service.mapper.DeliveryRequestPlaceMapper;
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
 * Integration tests for the {@link DeliveryRequestPlaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeliveryRequestPlaceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/delivery-request-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryRequestPlaceRepository deliveryRequestPlaceRepository;

    @Autowired
    private DeliveryRequestPlaceMapper deliveryRequestPlaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryRequestPlaceMockMvc;

    private DeliveryRequestPlace deliveryRequestPlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryRequestPlace createEntity(EntityManager em) {
        DeliveryRequestPlace deliveryRequestPlace = new DeliveryRequestPlace().name(DEFAULT_NAME);
        return deliveryRequestPlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryRequestPlace createUpdatedEntity(EntityManager em) {
        DeliveryRequestPlace deliveryRequestPlace = new DeliveryRequestPlace().name(UPDATED_NAME);
        return deliveryRequestPlace;
    }

    @BeforeEach
    public void initTest() {
        deliveryRequestPlace = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeCreate = deliveryRequestPlaceRepository.findAll().size();
        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);
        restDeliveryRequestPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryRequestPlace testDeliveryRequestPlace = deliveryRequestPlaceList.get(deliveryRequestPlaceList.size() - 1);
        assertThat(testDeliveryRequestPlace.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createDeliveryRequestPlaceWithExistingId() throws Exception {
        // Create the DeliveryRequestPlace with an existing ID
        deliveryRequestPlace.setId(1L);
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        int databaseSizeBeforeCreate = deliveryRequestPlaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryRequestPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveryRequestPlaces() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        // Get all the deliveryRequestPlaceList
        restDeliveryRequestPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryRequestPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getDeliveryRequestPlace() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        // Get the deliveryRequestPlace
        restDeliveryRequestPlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, deliveryRequestPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryRequestPlace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDeliveryRequestPlace() throws Exception {
        // Get the deliveryRequestPlace
        restDeliveryRequestPlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeliveryRequestPlace() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();

        // Update the deliveryRequestPlace
        DeliveryRequestPlace updatedDeliveryRequestPlace = deliveryRequestPlaceRepository.findById(deliveryRequestPlace.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryRequestPlace are not directly saved in db
        em.detach(updatedDeliveryRequestPlace);
        updatedDeliveryRequestPlace.name(UPDATED_NAME);
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(updatedDeliveryRequestPlace);

        restDeliveryRequestPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryRequestPlaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
        DeliveryRequestPlace testDeliveryRequestPlace = deliveryRequestPlaceList.get(deliveryRequestPlaceList.size() - 1);
        assertThat(testDeliveryRequestPlace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryRequestPlaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryRequestPlaceWithPatch() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();

        // Update the deliveryRequestPlace using partial update
        DeliveryRequestPlace partialUpdatedDeliveryRequestPlace = new DeliveryRequestPlace();
        partialUpdatedDeliveryRequestPlace.setId(deliveryRequestPlace.getId());

        partialUpdatedDeliveryRequestPlace.name(UPDATED_NAME);

        restDeliveryRequestPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryRequestPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryRequestPlace))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
        DeliveryRequestPlace testDeliveryRequestPlace = deliveryRequestPlaceList.get(deliveryRequestPlaceList.size() - 1);
        assertThat(testDeliveryRequestPlace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryRequestPlaceWithPatch() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();

        // Update the deliveryRequestPlace using partial update
        DeliveryRequestPlace partialUpdatedDeliveryRequestPlace = new DeliveryRequestPlace();
        partialUpdatedDeliveryRequestPlace.setId(deliveryRequestPlace.getId());

        partialUpdatedDeliveryRequestPlace.name(UPDATED_NAME);

        restDeliveryRequestPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryRequestPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryRequestPlace))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
        DeliveryRequestPlace testDeliveryRequestPlace = deliveryRequestPlaceList.get(deliveryRequestPlaceList.size() - 1);
        assertThat(testDeliveryRequestPlace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deliveryRequestPlaceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliveryRequestPlace() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRequestPlaceRepository.findAll().size();
        deliveryRequestPlace.setId(count.incrementAndGet());

        // Create the DeliveryRequestPlace
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = deliveryRequestPlaceMapper.toDto(deliveryRequestPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryRequestPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryRequestPlaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryRequestPlace in the database
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliveryRequestPlace() throws Exception {
        // Initialize the database
        deliveryRequestPlaceRepository.saveAndFlush(deliveryRequestPlace);

        int databaseSizeBeforeDelete = deliveryRequestPlaceRepository.findAll().size();

        // Delete the deliveryRequestPlace
        restDeliveryRequestPlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliveryRequestPlace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryRequestPlace> deliveryRequestPlaceList = deliveryRequestPlaceRepository.findAll();
        assertThat(deliveryRequestPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
