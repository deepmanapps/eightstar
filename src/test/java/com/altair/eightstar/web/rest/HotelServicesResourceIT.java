package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.HotelServices;
import com.altair.eightstar.repository.HotelServicesRepository;
import com.altair.eightstar.service.dto.HotelServicesDTO;
import com.altair.eightstar.service.mapper.HotelServicesMapper;
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
 * Integration tests for the {@link HotelServicesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelServicesResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_FOR_GUEST = false;
    private static final Boolean UPDATED_FOR_GUEST = true;

    private static final Float DEFAULT_SERVICE_PRICE = 1F;
    private static final Float UPDATED_SERVICE_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/hotel-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelServicesRepository hotelServicesRepository;

    @Autowired
    private HotelServicesMapper hotelServicesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelServicesMockMvc;

    private HotelServices hotelServices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelServices createEntity(EntityManager em) {
        HotelServices hotelServices = new HotelServices()
            .active(DEFAULT_ACTIVE)
            .forGuest(DEFAULT_FOR_GUEST)
            .servicePrice(DEFAULT_SERVICE_PRICE);
        return hotelServices;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelServices createUpdatedEntity(EntityManager em) {
        HotelServices hotelServices = new HotelServices()
            .active(UPDATED_ACTIVE)
            .forGuest(UPDATED_FOR_GUEST)
            .servicePrice(UPDATED_SERVICE_PRICE);
        return hotelServices;
    }

    @BeforeEach
    public void initTest() {
        hotelServices = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelServices() throws Exception {
        int databaseSizeBeforeCreate = hotelServicesRepository.findAll().size();
        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);
        restHotelServicesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeCreate + 1);
        HotelServices testHotelServices = hotelServicesList.get(hotelServicesList.size() - 1);
        assertThat(testHotelServices.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testHotelServices.getForGuest()).isEqualTo(DEFAULT_FOR_GUEST);
        assertThat(testHotelServices.getServicePrice()).isEqualTo(DEFAULT_SERVICE_PRICE);
    }

    @Test
    @Transactional
    void createHotelServicesWithExistingId() throws Exception {
        // Create the HotelServices with an existing ID
        hotelServices.setId(1L);
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        int databaseSizeBeforeCreate = hotelServicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelServicesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelServices() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        // Get all the hotelServicesList
        restHotelServicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].forGuest").value(hasItem(DEFAULT_FOR_GUEST.booleanValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getHotelServices() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        // Get the hotelServices
        restHotelServicesMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelServices.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.forGuest").value(DEFAULT_FOR_GUEST.booleanValue()))
            .andExpect(jsonPath("$.servicePrice").value(DEFAULT_SERVICE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHotelServices() throws Exception {
        // Get the hotelServices
        restHotelServicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotelServices() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();

        // Update the hotelServices
        HotelServices updatedHotelServices = hotelServicesRepository.findById(hotelServices.getId()).get();
        // Disconnect from session so that the updates on updatedHotelServices are not directly saved in db
        em.detach(updatedHotelServices);
        updatedHotelServices.active(UPDATED_ACTIVE).forGuest(UPDATED_FOR_GUEST).servicePrice(UPDATED_SERVICE_PRICE);
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(updatedHotelServices);

        restHotelServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelServicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
        HotelServices testHotelServices = hotelServicesList.get(hotelServicesList.size() - 1);
        assertThat(testHotelServices.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testHotelServices.getForGuest()).isEqualTo(UPDATED_FOR_GUEST);
        assertThat(testHotelServices.getServicePrice()).isEqualTo(UPDATED_SERVICE_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelServicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelServicesWithPatch() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();

        // Update the hotelServices using partial update
        HotelServices partialUpdatedHotelServices = new HotelServices();
        partialUpdatedHotelServices.setId(hotelServices.getId());

        partialUpdatedHotelServices.active(UPDATED_ACTIVE).forGuest(UPDATED_FOR_GUEST);

        restHotelServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelServices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelServices))
            )
            .andExpect(status().isOk());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
        HotelServices testHotelServices = hotelServicesList.get(hotelServicesList.size() - 1);
        assertThat(testHotelServices.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testHotelServices.getForGuest()).isEqualTo(UPDATED_FOR_GUEST);
        assertThat(testHotelServices.getServicePrice()).isEqualTo(DEFAULT_SERVICE_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateHotelServicesWithPatch() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();

        // Update the hotelServices using partial update
        HotelServices partialUpdatedHotelServices = new HotelServices();
        partialUpdatedHotelServices.setId(hotelServices.getId());

        partialUpdatedHotelServices.active(UPDATED_ACTIVE).forGuest(UPDATED_FOR_GUEST).servicePrice(UPDATED_SERVICE_PRICE);

        restHotelServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelServices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelServices))
            )
            .andExpect(status().isOk());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
        HotelServices testHotelServices = hotelServicesList.get(hotelServicesList.size() - 1);
        assertThat(testHotelServices.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testHotelServices.getForGuest()).isEqualTo(UPDATED_FOR_GUEST);
        assertThat(testHotelServices.getServicePrice()).isEqualTo(UPDATED_SERVICE_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelServicesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelServices() throws Exception {
        int databaseSizeBeforeUpdate = hotelServicesRepository.findAll().size();
        hotelServices.setId(count.incrementAndGet());

        // Create the HotelServices
        HotelServicesDTO hotelServicesDTO = hotelServicesMapper.toDto(hotelServices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelServicesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelServicesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelServices in the database
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelServices() throws Exception {
        // Initialize the database
        hotelServicesRepository.saveAndFlush(hotelServices);

        int databaseSizeBeforeDelete = hotelServicesRepository.findAll().size();

        // Delete the hotelServices
        restHotelServicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelServices.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelServices> hotelServicesList = hotelServicesRepository.findAll();
        assertThat(hotelServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
