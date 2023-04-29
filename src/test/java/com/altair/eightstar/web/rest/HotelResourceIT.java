package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.repository.HotelRepository;
import com.altair.eightstar.service.dto.HotelDTO;
import com.altair.eightstar.service.mapper.HotelMapper;
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
 * Integration tests for the {@link HotelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelResourceIT {

    private static final Long DEFAULT_HOTEL_ID = 1L;
    private static final Long UPDATED_HOTEL_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARS_NUMBER = 1;
    private static final Integer UPDATED_STARS_NUMBER = 2;

    private static final Double DEFAULT_EMERGENCY_NUMBER = 1D;
    private static final Double UPDATED_EMERGENCY_NUMBER = 2D;

    private static final String ENTITY_API_URL = "/api/hotels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .hotelId(DEFAULT_HOTEL_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .adresse(DEFAULT_ADRESSE)
            .starsNumber(DEFAULT_STARS_NUMBER)
            .emergencyNumber(DEFAULT_EMERGENCY_NUMBER);
        return hotel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createUpdatedEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .hotelId(UPDATED_HOTEL_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .starsNumber(UPDATED_STARS_NUMBER)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER);
        return hotel;
    }

    @BeforeEach
    public void initTest() {
        hotel = createEntity(em);
    }

    @Test
    @Transactional
    void createHotel() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();
        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);
        restHotelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelDTO)))
            .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate + 1);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getHotelId()).isEqualTo(DEFAULT_HOTEL_ID);
        assertThat(testHotel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHotel.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testHotel.getStarsNumber()).isEqualTo(DEFAULT_STARS_NUMBER);
        assertThat(testHotel.getEmergencyNumber()).isEqualTo(DEFAULT_EMERGENCY_NUMBER);
    }

    @Test
    @Transactional
    void createHotelWithExistingId() throws Exception {
        // Create the Hotel with an existing ID
        hotel.setId(1L);
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].hotelId").value(hasItem(DEFAULT_HOTEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].starsNumber").value(hasItem(DEFAULT_STARS_NUMBER)))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER.doubleValue())));
    }

    @Test
    @Transactional
    void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc
            .perform(get(ENTITY_API_URL_ID, hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.hotelId").value(DEFAULT_HOTEL_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.starsNumber").value(DEFAULT_STARS_NUMBER))
            .andExpect(jsonPath("$.emergencyNumber").value(DEFAULT_EMERGENCY_NUMBER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel
            .hotelId(UPDATED_HOTEL_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .starsNumber(UPDATED_STARS_NUMBER)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER);
        HotelDTO hotelDTO = hotelMapper.toDto(updatedHotel);

        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getHotelId()).isEqualTo(UPDATED_HOTEL_ID);
        assertThat(testHotel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHotel.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testHotel.getStarsNumber()).isEqualTo(UPDATED_STARS_NUMBER);
        assertThat(testHotel.getEmergencyNumber()).isEqualTo(UPDATED_EMERGENCY_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        partialUpdatedHotel.starsNumber(UPDATED_STARS_NUMBER);

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getHotelId()).isEqualTo(DEFAULT_HOTEL_ID);
        assertThat(testHotel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHotel.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testHotel.getStarsNumber()).isEqualTo(UPDATED_STARS_NUMBER);
        assertThat(testHotel.getEmergencyNumber()).isEqualTo(DEFAULT_EMERGENCY_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        partialUpdatedHotel
            .hotelId(UPDATED_HOTEL_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .starsNumber(UPDATED_STARS_NUMBER)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER);

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getHotelId()).isEqualTo(UPDATED_HOTEL_ID);
        assertThat(testHotel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHotel.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testHotel.getStarsNumber()).isEqualTo(UPDATED_STARS_NUMBER);
        assertThat(testHotel.getEmergencyNumber()).isEqualTo(UPDATED_EMERGENCY_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();
        hotel.setId(count.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hotelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeDelete = hotelRepository.findAll().size();

        // Delete the hotel
        restHotelMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
