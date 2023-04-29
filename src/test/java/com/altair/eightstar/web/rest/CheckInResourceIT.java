package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.CheckIn;
import com.altair.eightstar.domain.enumeration.PaymentMethod;
import com.altair.eightstar.domain.enumeration.Status;
import com.altair.eightstar.repository.CheckInRepository;
import com.altair.eightstar.service.dto.CheckInDTO;
import com.altair.eightstar.service.mapper.CheckInMapper;
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
 * Integration tests for the {@link CheckInResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckInResourceIT {

    private static final String DEFAULT_IDENTITY_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_PATH = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.CONFIRMED;
    private static final Status UPDATED_STATUS = Status.CANCELLED;

    private static final Double DEFAULT_DEPOSIT_AMOUNT = 1D;
    private static final Double UPDATED_DEPOSIT_AMOUNT = 2D;

    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.CCARD;
    private static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CASH;

    private static final Instant DEFAULT_ARRIVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEPARTURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ROOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SMOOKING = false;
    private static final Boolean UPDATED_SMOOKING = true;

    private static final Integer DEFAULT_ADULTS = 1;
    private static final Integer UPDATED_ADULTS = 2;

    private static final Integer DEFAULT_CHILDREN = 1;
    private static final Integer UPDATED_CHILDREN = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/check-ins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckInMockMvc;

    private CheckIn checkIn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckIn createEntity(EntityManager em) {
        CheckIn checkIn = new CheckIn()
            .identityPath(DEFAULT_IDENTITY_PATH)
            .status(DEFAULT_STATUS)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .roomType(DEFAULT_ROOM_TYPE)
            .smooking(DEFAULT_SMOOKING)
            .adults(DEFAULT_ADULTS)
            .children(DEFAULT_CHILDREN)
            .notes(DEFAULT_NOTES);
        return checkIn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckIn createUpdatedEntity(EntityManager em) {
        CheckIn checkIn = new CheckIn()
            .identityPath(UPDATED_IDENTITY_PATH)
            .status(UPDATED_STATUS)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .roomType(UPDATED_ROOM_TYPE)
            .smooking(UPDATED_SMOOKING)
            .adults(UPDATED_ADULTS)
            .children(UPDATED_CHILDREN)
            .notes(UPDATED_NOTES);
        return checkIn;
    }

    @BeforeEach
    public void initTest() {
        checkIn = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckIn() throws Exception {
        int databaseSizeBeforeCreate = checkInRepository.findAll().size();
        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);
        restCheckInMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate + 1);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getIdentityPath()).isEqualTo(DEFAULT_IDENTITY_PATH);
        assertThat(testCheckIn.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCheckIn.getDepositAmount()).isEqualTo(DEFAULT_DEPOSIT_AMOUNT);
        assertThat(testCheckIn.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testCheckIn.getArrivalDate()).isEqualTo(DEFAULT_ARRIVAL_DATE);
        assertThat(testCheckIn.getDepartureDate()).isEqualTo(DEFAULT_DEPARTURE_DATE);
        assertThat(testCheckIn.getRoomType()).isEqualTo(DEFAULT_ROOM_TYPE);
        assertThat(testCheckIn.getSmooking()).isEqualTo(DEFAULT_SMOOKING);
        assertThat(testCheckIn.getAdults()).isEqualTo(DEFAULT_ADULTS);
        assertThat(testCheckIn.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testCheckIn.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createCheckInWithExistingId() throws Exception {
        // Create the CheckIn with an existing ID
        checkIn.setId(1L);
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        int databaseSizeBeforeCreate = checkInRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckIns() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get all the checkInList
        restCheckInMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].identityPath").value(hasItem(DEFAULT_IDENTITY_PATH)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].roomType").value(hasItem(DEFAULT_ROOM_TYPE)))
            .andExpect(jsonPath("$.[*].smooking").value(hasItem(DEFAULT_SMOOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].adults").value(hasItem(DEFAULT_ADULTS)))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get the checkIn
        restCheckInMockMvc
            .perform(get(ENTITY_API_URL_ID, checkIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkIn.getId().intValue()))
            .andExpect(jsonPath("$.identityPath").value(DEFAULT_IDENTITY_PATH))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.toString()))
            .andExpect(jsonPath("$.roomType").value(DEFAULT_ROOM_TYPE))
            .andExpect(jsonPath("$.smooking").value(DEFAULT_SMOOKING.booleanValue()))
            .andExpect(jsonPath("$.adults").value(DEFAULT_ADULTS))
            .andExpect(jsonPath("$.children").value(DEFAULT_CHILDREN))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingCheckIn() throws Exception {
        // Get the checkIn
        restCheckInMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Update the checkIn
        CheckIn updatedCheckIn = checkInRepository.findById(checkIn.getId()).get();
        // Disconnect from session so that the updates on updatedCheckIn are not directly saved in db
        em.detach(updatedCheckIn);
        updatedCheckIn
            .identityPath(UPDATED_IDENTITY_PATH)
            .status(UPDATED_STATUS)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .roomType(UPDATED_ROOM_TYPE)
            .smooking(UPDATED_SMOOKING)
            .adults(UPDATED_ADULTS)
            .children(UPDATED_CHILDREN)
            .notes(UPDATED_NOTES);
        CheckInDTO checkInDTO = checkInMapper.toDto(updatedCheckIn);

        restCheckInMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkInDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getIdentityPath()).isEqualTo(UPDATED_IDENTITY_PATH);
        assertThat(testCheckIn.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCheckIn.getDepositAmount()).isEqualTo(UPDATED_DEPOSIT_AMOUNT);
        assertThat(testCheckIn.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCheckIn.getArrivalDate()).isEqualTo(UPDATED_ARRIVAL_DATE);
        assertThat(testCheckIn.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testCheckIn.getRoomType()).isEqualTo(UPDATED_ROOM_TYPE);
        assertThat(testCheckIn.getSmooking()).isEqualTo(UPDATED_SMOOKING);
        assertThat(testCheckIn.getAdults()).isEqualTo(UPDATED_ADULTS);
        assertThat(testCheckIn.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testCheckIn.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkInDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckInWithPatch() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Update the checkIn using partial update
        CheckIn partialUpdatedCheckIn = new CheckIn();
        partialUpdatedCheckIn.setId(checkIn.getId());

        partialUpdatedCheckIn.paymentMethod(UPDATED_PAYMENT_METHOD).departureDate(UPDATED_DEPARTURE_DATE).adults(UPDATED_ADULTS);

        restCheckInMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckIn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckIn))
            )
            .andExpect(status().isOk());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getIdentityPath()).isEqualTo(DEFAULT_IDENTITY_PATH);
        assertThat(testCheckIn.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCheckIn.getDepositAmount()).isEqualTo(DEFAULT_DEPOSIT_AMOUNT);
        assertThat(testCheckIn.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCheckIn.getArrivalDate()).isEqualTo(DEFAULT_ARRIVAL_DATE);
        assertThat(testCheckIn.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testCheckIn.getRoomType()).isEqualTo(DEFAULT_ROOM_TYPE);
        assertThat(testCheckIn.getSmooking()).isEqualTo(DEFAULT_SMOOKING);
        assertThat(testCheckIn.getAdults()).isEqualTo(UPDATED_ADULTS);
        assertThat(testCheckIn.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testCheckIn.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateCheckInWithPatch() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Update the checkIn using partial update
        CheckIn partialUpdatedCheckIn = new CheckIn();
        partialUpdatedCheckIn.setId(checkIn.getId());

        partialUpdatedCheckIn
            .identityPath(UPDATED_IDENTITY_PATH)
            .status(UPDATED_STATUS)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .roomType(UPDATED_ROOM_TYPE)
            .smooking(UPDATED_SMOOKING)
            .adults(UPDATED_ADULTS)
            .children(UPDATED_CHILDREN)
            .notes(UPDATED_NOTES);

        restCheckInMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckIn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckIn))
            )
            .andExpect(status().isOk());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getIdentityPath()).isEqualTo(UPDATED_IDENTITY_PATH);
        assertThat(testCheckIn.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCheckIn.getDepositAmount()).isEqualTo(UPDATED_DEPOSIT_AMOUNT);
        assertThat(testCheckIn.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCheckIn.getArrivalDate()).isEqualTo(UPDATED_ARRIVAL_DATE);
        assertThat(testCheckIn.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testCheckIn.getRoomType()).isEqualTo(UPDATED_ROOM_TYPE);
        assertThat(testCheckIn.getSmooking()).isEqualTo(UPDATED_SMOOKING);
        assertThat(testCheckIn.getAdults()).isEqualTo(UPDATED_ADULTS);
        assertThat(testCheckIn.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testCheckIn.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkInDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();
        checkIn.setId(count.incrementAndGet());

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.toDto(checkIn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckInMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkInDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        int databaseSizeBeforeDelete = checkInRepository.findAll().size();

        // Delete the checkIn
        restCheckInMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkIn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
