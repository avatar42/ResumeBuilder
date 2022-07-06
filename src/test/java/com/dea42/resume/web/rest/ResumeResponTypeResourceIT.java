package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeResponType;
import com.dea42.resume.repository.ResumeResponTypeRepository;
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
 * Integration tests for the {@link ResumeResponTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeResponTypeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resume-respon-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeResponTypeRepository resumeResponTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeResponTypeMockMvc;

    private ResumeResponType resumeResponType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeResponType createEntity(EntityManager em) {
        ResumeResponType resumeResponType = new ResumeResponType().description(DEFAULT_DESCRIPTION);
        return resumeResponType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeResponType createUpdatedEntity(EntityManager em) {
        ResumeResponType resumeResponType = new ResumeResponType().description(UPDATED_DESCRIPTION);
        return resumeResponType;
    }

    @BeforeEach
    public void initTest() {
        resumeResponType = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeResponType() throws Exception {
        int databaseSizeBeforeCreate = resumeResponTypeRepository.findAll().size();
        // Create the ResumeResponType
        restResumeResponTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isCreated());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeResponType testResumeResponType = resumeResponTypeList.get(resumeResponTypeList.size() - 1);
        assertThat(testResumeResponType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createResumeResponTypeWithExistingId() throws Exception {
        // Create the ResumeResponType with an existing ID
        resumeResponType.setId(1L);

        int databaseSizeBeforeCreate = resumeResponTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeResponTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResumeResponTypes() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        // Get all the resumeResponTypeList
        restResumeResponTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeResponType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getResumeResponType() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        // Get the resumeResponType
        restResumeResponTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeResponType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeResponType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingResumeResponType() throws Exception {
        // Get the resumeResponType
        restResumeResponTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeResponType() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();

        // Update the resumeResponType
        ResumeResponType updatedResumeResponType = resumeResponTypeRepository.findById(resumeResponType.getId()).get();
        // Disconnect from session so that the updates on updatedResumeResponType are not directly saved in db
        em.detach(updatedResumeResponType);
        updatedResumeResponType.description(UPDATED_DESCRIPTION);

        restResumeResponTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeResponType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeResponType))
            )
            .andExpect(status().isOk());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
        ResumeResponType testResumeResponType = resumeResponTypeList.get(resumeResponTypeList.size() - 1);
        assertThat(testResumeResponType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeResponType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeResponTypeWithPatch() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();

        // Update the resumeResponType using partial update
        ResumeResponType partialUpdatedResumeResponType = new ResumeResponType();
        partialUpdatedResumeResponType.setId(resumeResponType.getId());

        partialUpdatedResumeResponType.description(UPDATED_DESCRIPTION);

        restResumeResponTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeResponType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeResponType))
            )
            .andExpect(status().isOk());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
        ResumeResponType testResumeResponType = resumeResponTypeList.get(resumeResponTypeList.size() - 1);
        assertThat(testResumeResponType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateResumeResponTypeWithPatch() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();

        // Update the resumeResponType using partial update
        ResumeResponType partialUpdatedResumeResponType = new ResumeResponType();
        partialUpdatedResumeResponType.setId(resumeResponType.getId());

        partialUpdatedResumeResponType.description(UPDATED_DESCRIPTION);

        restResumeResponTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeResponType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeResponType))
            )
            .andExpect(status().isOk());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
        ResumeResponType testResumeResponType = resumeResponTypeList.get(resumeResponTypeList.size() - 1);
        assertThat(testResumeResponType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeResponType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeResponType() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponTypeRepository.findAll().size();
        resumeResponType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeResponType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeResponType in the database
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeResponType() throws Exception {
        // Initialize the database
        resumeResponTypeRepository.saveAndFlush(resumeResponType);

        int databaseSizeBeforeDelete = resumeResponTypeRepository.findAll().size();

        // Delete the resumeResponType
        restResumeResponTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeResponType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeResponType> resumeResponTypeList = resumeResponTypeRepository.findAll();
        assertThat(resumeResponTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
