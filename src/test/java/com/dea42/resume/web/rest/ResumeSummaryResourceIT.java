package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeSummary;
import com.dea42.resume.repository.ResumeSummaryRepository;
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
 * Integration tests for the {@link ResumeSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeSummaryResourceIT {

    private static final String DEFAULT_SUMMARY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUMMARY_ORDER = 1;
    private static final Integer UPDATED_SUMMARY_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/resume-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeSummaryRepository resumeSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeSummaryMockMvc;

    private ResumeSummary resumeSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSummary createEntity(EntityManager em) {
        ResumeSummary resumeSummary = new ResumeSummary().summaryName(DEFAULT_SUMMARY_NAME).summaryOrder(DEFAULT_SUMMARY_ORDER);
        return resumeSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSummary createUpdatedEntity(EntityManager em) {
        ResumeSummary resumeSummary = new ResumeSummary().summaryName(UPDATED_SUMMARY_NAME).summaryOrder(UPDATED_SUMMARY_ORDER);
        return resumeSummary;
    }

    @BeforeEach
    public void initTest() {
        resumeSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeSummary() throws Exception {
        int databaseSizeBeforeCreate = resumeSummaryRepository.findAll().size();
        // Create the ResumeSummary
        restResumeSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSummary)))
            .andExpect(status().isCreated());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeSummary testResumeSummary = resumeSummaryList.get(resumeSummaryList.size() - 1);
        assertThat(testResumeSummary.getSummaryName()).isEqualTo(DEFAULT_SUMMARY_NAME);
        assertThat(testResumeSummary.getSummaryOrder()).isEqualTo(DEFAULT_SUMMARY_ORDER);
    }

    @Test
    @Transactional
    void createResumeSummaryWithExistingId() throws Exception {
        // Create the ResumeSummary with an existing ID
        resumeSummary.setId(1L);

        int databaseSizeBeforeCreate = resumeSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSummary)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSummaryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSummaryRepository.findAll().size();
        // set the field null
        resumeSummary.setSummaryName(null);

        // Create the ResumeSummary, which fails.

        restResumeSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSummary)))
            .andExpect(status().isBadRequest());

        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSummaryOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSummaryRepository.findAll().size();
        // set the field null
        resumeSummary.setSummaryOrder(null);

        // Create the ResumeSummary, which fails.

        restResumeSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSummary)))
            .andExpect(status().isBadRequest());

        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumeSummaries() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        // Get all the resumeSummaryList
        restResumeSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].summaryName").value(hasItem(DEFAULT_SUMMARY_NAME)))
            .andExpect(jsonPath("$.[*].summaryOrder").value(hasItem(DEFAULT_SUMMARY_ORDER)));
    }

    @Test
    @Transactional
    void getResumeSummary() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        // Get the resumeSummary
        restResumeSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeSummary.getId().intValue()))
            .andExpect(jsonPath("$.summaryName").value(DEFAULT_SUMMARY_NAME))
            .andExpect(jsonPath("$.summaryOrder").value(DEFAULT_SUMMARY_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingResumeSummary() throws Exception {
        // Get the resumeSummary
        restResumeSummaryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeSummary() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();

        // Update the resumeSummary
        ResumeSummary updatedResumeSummary = resumeSummaryRepository.findById(resumeSummary.getId()).get();
        // Disconnect from session so that the updates on updatedResumeSummary are not directly saved in db
        em.detach(updatedResumeSummary);
        updatedResumeSummary.summaryName(UPDATED_SUMMARY_NAME).summaryOrder(UPDATED_SUMMARY_ORDER);

        restResumeSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeSummary))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
        ResumeSummary testResumeSummary = resumeSummaryList.get(resumeSummaryList.size() - 1);
        assertThat(testResumeSummary.getSummaryName()).isEqualTo(UPDATED_SUMMARY_NAME);
        assertThat(testResumeSummary.getSummaryOrder()).isEqualTo(UPDATED_SUMMARY_ORDER);
    }

    @Test
    @Transactional
    void putNonExistingResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSummary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeSummaryWithPatch() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();

        // Update the resumeSummary using partial update
        ResumeSummary partialUpdatedResumeSummary = new ResumeSummary();
        partialUpdatedResumeSummary.setId(resumeSummary.getId());

        partialUpdatedResumeSummary.summaryOrder(UPDATED_SUMMARY_ORDER);

        restResumeSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSummary))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
        ResumeSummary testResumeSummary = resumeSummaryList.get(resumeSummaryList.size() - 1);
        assertThat(testResumeSummary.getSummaryName()).isEqualTo(DEFAULT_SUMMARY_NAME);
        assertThat(testResumeSummary.getSummaryOrder()).isEqualTo(UPDATED_SUMMARY_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateResumeSummaryWithPatch() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();

        // Update the resumeSummary using partial update
        ResumeSummary partialUpdatedResumeSummary = new ResumeSummary();
        partialUpdatedResumeSummary.setId(resumeSummary.getId());

        partialUpdatedResumeSummary.summaryName(UPDATED_SUMMARY_NAME).summaryOrder(UPDATED_SUMMARY_ORDER);

        restResumeSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSummary))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
        ResumeSummary testResumeSummary = resumeSummaryList.get(resumeSummaryList.size() - 1);
        assertThat(testResumeSummary.getSummaryName()).isEqualTo(UPDATED_SUMMARY_NAME);
        assertThat(testResumeSummary.getSummaryOrder()).isEqualTo(UPDATED_SUMMARY_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeSummary() throws Exception {
        int databaseSizeBeforeUpdate = resumeSummaryRepository.findAll().size();
        resumeSummary.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSummary in the database
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeSummary() throws Exception {
        // Initialize the database
        resumeSummaryRepository.saveAndFlush(resumeSummary);

        int databaseSizeBeforeDelete = resumeSummaryRepository.findAll().size();

        // Delete the resumeSummary
        restResumeSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeSummary.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeSummary> resumeSummaryList = resumeSummaryRepository.findAll();
        assertThat(resumeSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
