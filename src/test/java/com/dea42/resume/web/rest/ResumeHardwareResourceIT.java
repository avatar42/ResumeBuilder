package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeHardware;
import com.dea42.resume.repository.ResumeHardwareRepository;
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
 * Integration tests for the {@link ResumeHardwareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeHardwareResourceIT {

    private static final Integer DEFAULT_REPAIR_CERTIFIED = 1;
    private static final Integer UPDATED_REPAIR_CERTIFIED = 2;

    private static final Integer DEFAULT_SHOW_SUM = 1;
    private static final Integer UPDATED_SHOW_SUM = 2;

    private static final String DEFAULT_HARDWARE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HARDWARE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resume-hardwares";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeHardwareRepository resumeHardwareRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeHardwareMockMvc;

    private ResumeHardware resumeHardware;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeHardware createEntity(EntityManager em) {
        ResumeHardware resumeHardware = new ResumeHardware()
            .repairCertified(DEFAULT_REPAIR_CERTIFIED)
            .showSum(DEFAULT_SHOW_SUM)
            .hardwareName(DEFAULT_HARDWARE_NAME);
        return resumeHardware;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeHardware createUpdatedEntity(EntityManager em) {
        ResumeHardware resumeHardware = new ResumeHardware()
            .repairCertified(UPDATED_REPAIR_CERTIFIED)
            .showSum(UPDATED_SHOW_SUM)
            .hardwareName(UPDATED_HARDWARE_NAME);
        return resumeHardware;
    }

    @BeforeEach
    public void initTest() {
        resumeHardware = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeHardware() throws Exception {
        int databaseSizeBeforeCreate = resumeHardwareRepository.findAll().size();
        // Create the ResumeHardware
        restResumeHardwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isCreated());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeHardware testResumeHardware = resumeHardwareList.get(resumeHardwareList.size() - 1);
        assertThat(testResumeHardware.getRepairCertified()).isEqualTo(DEFAULT_REPAIR_CERTIFIED);
        assertThat(testResumeHardware.getShowSum()).isEqualTo(DEFAULT_SHOW_SUM);
        assertThat(testResumeHardware.getHardwareName()).isEqualTo(DEFAULT_HARDWARE_NAME);
    }

    @Test
    @Transactional
    void createResumeHardwareWithExistingId() throws Exception {
        // Create the ResumeHardware with an existing ID
        resumeHardware.setId(1L);

        int databaseSizeBeforeCreate = resumeHardwareRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeHardwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRepairCertifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHardwareRepository.findAll().size();
        // set the field null
        resumeHardware.setRepairCertified(null);

        // Create the ResumeHardware, which fails.

        restResumeHardwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShowSumIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHardwareRepository.findAll().size();
        // set the field null
        resumeHardware.setShowSum(null);

        // Create the ResumeHardware, which fails.

        restResumeHardwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHardwareNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHardwareRepository.findAll().size();
        // set the field null
        resumeHardware.setHardwareName(null);

        // Create the ResumeHardware, which fails.

        restResumeHardwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumeHardwares() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        // Get all the resumeHardwareList
        restResumeHardwareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeHardware.getId().intValue())))
            .andExpect(jsonPath("$.[*].repairCertified").value(hasItem(DEFAULT_REPAIR_CERTIFIED)))
            .andExpect(jsonPath("$.[*].showSum").value(hasItem(DEFAULT_SHOW_SUM)))
            .andExpect(jsonPath("$.[*].hardwareName").value(hasItem(DEFAULT_HARDWARE_NAME)));
    }

    @Test
    @Transactional
    void getResumeHardware() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        // Get the resumeHardware
        restResumeHardwareMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeHardware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeHardware.getId().intValue()))
            .andExpect(jsonPath("$.repairCertified").value(DEFAULT_REPAIR_CERTIFIED))
            .andExpect(jsonPath("$.showSum").value(DEFAULT_SHOW_SUM))
            .andExpect(jsonPath("$.hardwareName").value(DEFAULT_HARDWARE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingResumeHardware() throws Exception {
        // Get the resumeHardware
        restResumeHardwareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeHardware() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();

        // Update the resumeHardware
        ResumeHardware updatedResumeHardware = resumeHardwareRepository.findById(resumeHardware.getId()).get();
        // Disconnect from session so that the updates on updatedResumeHardware are not directly saved in db
        em.detach(updatedResumeHardware);
        updatedResumeHardware.repairCertified(UPDATED_REPAIR_CERTIFIED).showSum(UPDATED_SHOW_SUM).hardwareName(UPDATED_HARDWARE_NAME);

        restResumeHardwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeHardware.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeHardware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardware testResumeHardware = resumeHardwareList.get(resumeHardwareList.size() - 1);
        assertThat(testResumeHardware.getRepairCertified()).isEqualTo(UPDATED_REPAIR_CERTIFIED);
        assertThat(testResumeHardware.getShowSum()).isEqualTo(UPDATED_SHOW_SUM);
        assertThat(testResumeHardware.getHardwareName()).isEqualTo(UPDATED_HARDWARE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeHardware.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardware)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeHardwareWithPatch() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();

        // Update the resumeHardware using partial update
        ResumeHardware partialUpdatedResumeHardware = new ResumeHardware();
        partialUpdatedResumeHardware.setId(resumeHardware.getId());

        partialUpdatedResumeHardware.hardwareName(UPDATED_HARDWARE_NAME);

        restResumeHardwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeHardware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeHardware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardware testResumeHardware = resumeHardwareList.get(resumeHardwareList.size() - 1);
        assertThat(testResumeHardware.getRepairCertified()).isEqualTo(DEFAULT_REPAIR_CERTIFIED);
        assertThat(testResumeHardware.getShowSum()).isEqualTo(DEFAULT_SHOW_SUM);
        assertThat(testResumeHardware.getHardwareName()).isEqualTo(UPDATED_HARDWARE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateResumeHardwareWithPatch() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();

        // Update the resumeHardware using partial update
        ResumeHardware partialUpdatedResumeHardware = new ResumeHardware();
        partialUpdatedResumeHardware.setId(resumeHardware.getId());

        partialUpdatedResumeHardware
            .repairCertified(UPDATED_REPAIR_CERTIFIED)
            .showSum(UPDATED_SHOW_SUM)
            .hardwareName(UPDATED_HARDWARE_NAME);

        restResumeHardwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeHardware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeHardware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardware testResumeHardware = resumeHardwareList.get(resumeHardwareList.size() - 1);
        assertThat(testResumeHardware.getRepairCertified()).isEqualTo(UPDATED_REPAIR_CERTIFIED);
        assertThat(testResumeHardware.getShowSum()).isEqualTo(UPDATED_SHOW_SUM);
        assertThat(testResumeHardware.getHardwareName()).isEqualTo(UPDATED_HARDWARE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeHardware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeHardware() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardwareRepository.findAll().size();
        resumeHardware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardwareMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeHardware))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeHardware in the database
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeHardware() throws Exception {
        // Initialize the database
        resumeHardwareRepository.saveAndFlush(resumeHardware);

        int databaseSizeBeforeDelete = resumeHardwareRepository.findAll().size();

        // Delete the resumeHardware
        restResumeHardwareMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeHardware.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeHardware> resumeHardwareList = resumeHardwareRepository.findAll();
        assertThat(resumeHardwareList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
