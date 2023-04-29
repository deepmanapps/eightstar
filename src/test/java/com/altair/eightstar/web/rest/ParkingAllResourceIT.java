package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.ParkingAll;
import com.altair.eightstar.repository.ParkingAllRepository;
import com.altair.eightstar.service.dto.ParkingAllDTO;
import com.altair.eightstar.service.mapper.ParkingAllMapper;
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
 * Integration tests for the {@link ParkingAllResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkingAllResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parking-alls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParkingAllRepository parkingAllRepository;

    @Autowired
    private ParkingAllMapper parkingAllMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkingAllMockMvc;

    private ParkingAll parkingAll;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingAll createEntity(EntityManager em) {
        ParkingAll parkingAll = new ParkingAll().name(DEFAULT_NAME);
        return parkingAll;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingAll createUpdatedEntity(EntityManager em) {
        ParkingAll parkingAll = new ParkingAll().name(UPDATED_NAME);
        return parkingAll;
    }

    @BeforeEach
    public void initTest() {
        parkingAll = createEntity(em);
    }

    @Test
    @Transactional
    void createParkingAll() throws Exception {
        int databaseSizeBeforeCreate = parkingAllRepository.findAll().size();
        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);
        restParkingAllMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingAllDTO)))
            .andExpect(status().isCreated());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeCreate + 1);
        ParkingAll testParkingAll = parkingAllList.get(parkingAllList.size() - 1);
        assertThat(testParkingAll.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createParkingAllWithExistingId() throws Exception {
        // Create the ParkingAll with an existing ID
        parkingAll.setId(1L);
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        int databaseSizeBeforeCreate = parkingAllRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingAllMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingAllDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkingAlls() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        // Get all the parkingAllList
        restParkingAllMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingAll.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getParkingAll() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        // Get the parkingAll
        restParkingAllMockMvc
            .perform(get(ENTITY_API_URL_ID, parkingAll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkingAll.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingParkingAll() throws Exception {
        // Get the parkingAll
        restParkingAllMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkingAll() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();

        // Update the parkingAll
        ParkingAll updatedParkingAll = parkingAllRepository.findById(parkingAll.getId()).get();
        // Disconnect from session so that the updates on updatedParkingAll are not directly saved in db
        em.detach(updatedParkingAll);
        updatedParkingAll.name(UPDATED_NAME);
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(updatedParkingAll);

        restParkingAllMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingAllDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
        ParkingAll testParkingAll = parkingAllList.get(parkingAllList.size() - 1);
        assertThat(testParkingAll.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingAllDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingAllDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkingAllWithPatch() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();

        // Update the parkingAll using partial update
        ParkingAll partialUpdatedParkingAll = new ParkingAll();
        partialUpdatedParkingAll.setId(parkingAll.getId());

        partialUpdatedParkingAll.name(UPDATED_NAME);

        restParkingAllMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingAll.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingAll))
            )
            .andExpect(status().isOk());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
        ParkingAll testParkingAll = parkingAllList.get(parkingAllList.size() - 1);
        assertThat(testParkingAll.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateParkingAllWithPatch() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();

        // Update the parkingAll using partial update
        ParkingAll partialUpdatedParkingAll = new ParkingAll();
        partialUpdatedParkingAll.setId(parkingAll.getId());

        partialUpdatedParkingAll.name(UPDATED_NAME);

        restParkingAllMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingAll.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingAll))
            )
            .andExpect(status().isOk());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
        ParkingAll testParkingAll = parkingAllList.get(parkingAllList.size() - 1);
        assertThat(testParkingAll.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkingAllDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkingAll() throws Exception {
        int databaseSizeBeforeUpdate = parkingAllRepository.findAll().size();
        parkingAll.setId(count.incrementAndGet());

        // Create the ParkingAll
        ParkingAllDTO parkingAllDTO = parkingAllMapper.toDto(parkingAll);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingAllMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parkingAllDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingAll in the database
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkingAll() throws Exception {
        // Initialize the database
        parkingAllRepository.saveAndFlush(parkingAll);

        int databaseSizeBeforeDelete = parkingAllRepository.findAll().size();

        // Delete the parkingAll
        restParkingAllMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkingAll.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParkingAll> parkingAllList = parkingAllRepository.findAll();
        assertThat(parkingAllList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
