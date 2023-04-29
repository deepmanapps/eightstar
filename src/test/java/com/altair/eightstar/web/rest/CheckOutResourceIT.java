package com.altair.eightstar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.altair.eightstar.IntegrationTest;
import com.altair.eightstar.domain.CheckOut;
import com.altair.eightstar.repository.CheckOutRepository;
import com.altair.eightstar.service.dto.CheckOutDTO;
import com.altair.eightstar.service.mapper.CheckOutMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CheckOutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckOutResourceIT {

    private static final String DEFAULT_ROOM_CLEARANCE = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_CLEARANCE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REVIEW = "BBBBBBBBBB";

    private static final String DEFAULT_MINI_BAR_CLEARANCE = "AAAAAAAAAA";
    private static final String UPDATED_MINI_BAR_CLEARANCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LATE_CHECK_OUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LATE_CHECK_OUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_LATE = false;
    private static final Boolean UPDATED_IS_LATE = true;

    private static final Boolean DEFAULT_IS_COLLECTED_DEPOSIT_AMOUNT = false;
    private static final Boolean UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT = true;

    private static final Double DEFAULT_COLLECTED_AMOUNT = 1D;
    private static final Double UPDATED_COLLECTED_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/check-outs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private CheckOutMapper checkOutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckOutMockMvc;

    private CheckOut checkOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckOut createEntity(EntityManager em) {
        CheckOut checkOut = new CheckOut()
            .roomClearance(DEFAULT_ROOM_CLEARANCE)
            .customerReview(DEFAULT_CUSTOMER_REVIEW)
            .miniBarClearance(DEFAULT_MINI_BAR_CLEARANCE)
            .lateCheckOut(DEFAULT_LATE_CHECK_OUT)
            .isLate(DEFAULT_IS_LATE)
            .isCollectedDepositAmount(DEFAULT_IS_COLLECTED_DEPOSIT_AMOUNT)
            .collectedAmount(DEFAULT_COLLECTED_AMOUNT);
        return checkOut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckOut createUpdatedEntity(EntityManager em) {
        CheckOut checkOut = new CheckOut()
            .roomClearance(UPDATED_ROOM_CLEARANCE)
            .customerReview(UPDATED_CUSTOMER_REVIEW)
            .miniBarClearance(UPDATED_MINI_BAR_CLEARANCE)
            .lateCheckOut(UPDATED_LATE_CHECK_OUT)
            .isLate(UPDATED_IS_LATE)
            .isCollectedDepositAmount(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT)
            .collectedAmount(UPDATED_COLLECTED_AMOUNT);
        return checkOut;
    }

    @BeforeEach
    public void initTest() {
        checkOut = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckOut() throws Exception {
        int databaseSizeBeforeCreate = checkOutRepository.findAll().size();
        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);
        restCheckOutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkOutDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeCreate + 1);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getRoomClearance()).isEqualTo(DEFAULT_ROOM_CLEARANCE);
        assertThat(testCheckOut.getCustomerReview()).isEqualTo(DEFAULT_CUSTOMER_REVIEW);
        assertThat(testCheckOut.getMiniBarClearance()).isEqualTo(DEFAULT_MINI_BAR_CLEARANCE);
        assertThat(testCheckOut.getLateCheckOut()).isEqualTo(DEFAULT_LATE_CHECK_OUT);
        assertThat(testCheckOut.getIsLate()).isEqualTo(DEFAULT_IS_LATE);
        assertThat(testCheckOut.getIsCollectedDepositAmount()).isEqualTo(DEFAULT_IS_COLLECTED_DEPOSIT_AMOUNT);
        assertThat(testCheckOut.getCollectedAmount()).isEqualTo(DEFAULT_COLLECTED_AMOUNT);
    }

    @Test
    @Transactional
    void createCheckOutWithExistingId() throws Exception {
        // Create the CheckOut with an existing ID
        checkOut.setId(1L);
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        int databaseSizeBeforeCreate = checkOutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckOutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckOuts() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomClearance").value(hasItem(DEFAULT_ROOM_CLEARANCE.toString())))
            .andExpect(jsonPath("$.[*].customerReview").value(hasItem(DEFAULT_CUSTOMER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].miniBarClearance").value(hasItem(DEFAULT_MINI_BAR_CLEARANCE.toString())))
            .andExpect(jsonPath("$.[*].lateCheckOut").value(hasItem(DEFAULT_LATE_CHECK_OUT.toString())))
            .andExpect(jsonPath("$.[*].isLate").value(hasItem(DEFAULT_IS_LATE.booleanValue())))
            .andExpect(jsonPath("$.[*].isCollectedDepositAmount").value(hasItem(DEFAULT_IS_COLLECTED_DEPOSIT_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].collectedAmount").value(hasItem(DEFAULT_COLLECTED_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get the checkOut
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL_ID, checkOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkOut.getId().intValue()))
            .andExpect(jsonPath("$.roomClearance").value(DEFAULT_ROOM_CLEARANCE.toString()))
            .andExpect(jsonPath("$.customerReview").value(DEFAULT_CUSTOMER_REVIEW.toString()))
            .andExpect(jsonPath("$.miniBarClearance").value(DEFAULT_MINI_BAR_CLEARANCE.toString()))
            .andExpect(jsonPath("$.lateCheckOut").value(DEFAULT_LATE_CHECK_OUT.toString()))
            .andExpect(jsonPath("$.isLate").value(DEFAULT_IS_LATE.booleanValue()))
            .andExpect(jsonPath("$.isCollectedDepositAmount").value(DEFAULT_IS_COLLECTED_DEPOSIT_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.collectedAmount").value(DEFAULT_COLLECTED_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCheckOut() throws Exception {
        // Get the checkOut
        restCheckOutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut
        CheckOut updatedCheckOut = checkOutRepository.findById(checkOut.getId()).get();
        // Disconnect from session so that the updates on updatedCheckOut are not directly saved in db
        em.detach(updatedCheckOut);
        updatedCheckOut
            .roomClearance(UPDATED_ROOM_CLEARANCE)
            .customerReview(UPDATED_CUSTOMER_REVIEW)
            .miniBarClearance(UPDATED_MINI_BAR_CLEARANCE)
            .lateCheckOut(UPDATED_LATE_CHECK_OUT)
            .isLate(UPDATED_IS_LATE)
            .isCollectedDepositAmount(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT)
            .collectedAmount(UPDATED_COLLECTED_AMOUNT);
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(updatedCheckOut);

        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getRoomClearance()).isEqualTo(UPDATED_ROOM_CLEARANCE);
        assertThat(testCheckOut.getCustomerReview()).isEqualTo(UPDATED_CUSTOMER_REVIEW);
        assertThat(testCheckOut.getMiniBarClearance()).isEqualTo(UPDATED_MINI_BAR_CLEARANCE);
        assertThat(testCheckOut.getLateCheckOut()).isEqualTo(UPDATED_LATE_CHECK_OUT);
        assertThat(testCheckOut.getIsLate()).isEqualTo(UPDATED_IS_LATE);
        assertThat(testCheckOut.getIsCollectedDepositAmount()).isEqualTo(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT);
        assertThat(testCheckOut.getCollectedAmount()).isEqualTo(UPDATED_COLLECTED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkOutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckOutWithPatch() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut using partial update
        CheckOut partialUpdatedCheckOut = new CheckOut();
        partialUpdatedCheckOut.setId(checkOut.getId());

        partialUpdatedCheckOut
            .roomClearance(UPDATED_ROOM_CLEARANCE)
            .isCollectedDepositAmount(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT)
            .collectedAmount(UPDATED_COLLECTED_AMOUNT);

        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckOut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckOut))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getRoomClearance()).isEqualTo(UPDATED_ROOM_CLEARANCE);
        assertThat(testCheckOut.getCustomerReview()).isEqualTo(DEFAULT_CUSTOMER_REVIEW);
        assertThat(testCheckOut.getMiniBarClearance()).isEqualTo(DEFAULT_MINI_BAR_CLEARANCE);
        assertThat(testCheckOut.getLateCheckOut()).isEqualTo(DEFAULT_LATE_CHECK_OUT);
        assertThat(testCheckOut.getIsLate()).isEqualTo(DEFAULT_IS_LATE);
        assertThat(testCheckOut.getIsCollectedDepositAmount()).isEqualTo(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT);
        assertThat(testCheckOut.getCollectedAmount()).isEqualTo(UPDATED_COLLECTED_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateCheckOutWithPatch() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut using partial update
        CheckOut partialUpdatedCheckOut = new CheckOut();
        partialUpdatedCheckOut.setId(checkOut.getId());

        partialUpdatedCheckOut
            .roomClearance(UPDATED_ROOM_CLEARANCE)
            .customerReview(UPDATED_CUSTOMER_REVIEW)
            .miniBarClearance(UPDATED_MINI_BAR_CLEARANCE)
            .lateCheckOut(UPDATED_LATE_CHECK_OUT)
            .isLate(UPDATED_IS_LATE)
            .isCollectedDepositAmount(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT)
            .collectedAmount(UPDATED_COLLECTED_AMOUNT);

        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckOut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckOut))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getRoomClearance()).isEqualTo(UPDATED_ROOM_CLEARANCE);
        assertThat(testCheckOut.getCustomerReview()).isEqualTo(UPDATED_CUSTOMER_REVIEW);
        assertThat(testCheckOut.getMiniBarClearance()).isEqualTo(UPDATED_MINI_BAR_CLEARANCE);
        assertThat(testCheckOut.getLateCheckOut()).isEqualTo(UPDATED_LATE_CHECK_OUT);
        assertThat(testCheckOut.getIsLate()).isEqualTo(UPDATED_IS_LATE);
        assertThat(testCheckOut.getIsCollectedDepositAmount()).isEqualTo(UPDATED_IS_COLLECTED_DEPOSIT_AMOUNT);
        assertThat(testCheckOut.getCollectedAmount()).isEqualTo(UPDATED_COLLECTED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeDelete = checkOutRepository.findAll().size();

        // Delete the checkOut
        restCheckOutMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkOut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
