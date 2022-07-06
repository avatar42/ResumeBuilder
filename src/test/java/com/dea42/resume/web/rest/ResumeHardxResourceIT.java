package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeHardx;
import com.dea42.resume.repository.ResumeHardxRepository;
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
 * Integration tests for the {@link ResumeHardxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeHardxResourceIT {

    private static final String ENTITY_API_URL = "/api/resume-hardxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeHardxRepository resumeHardxRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeHardxMockMvc;

    private ResumeHardx resumeHardx;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeHardx createEntity(EntityManager em) {
        ResumeHardx resumeHardx = new ResumeHardx();
        return resumeHardx;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeHardx createUpdatedEntity(EntityManager em) {
        ResumeHardx resumeHardx = new ResumeHardx();
        return resumeHardx;
    }

    @BeforeEach
    public void initTest() {
        resumeHardx = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeHardx() throws Exception {
        int databaseSizeBeforeCreate = resumeHardxRepository.findAll().size();
        // Create the ResumeHardx
        restResumeHardxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardx)))
            .andExpect(status().isCreated());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeHardx testResumeHardx = resumeHardxList.get(resumeHardxList.size() - 1);
    }

    @Test
    @Transactional
    void createResumeHardxWithExistingId() throws Exception {
        // Create the ResumeHardx with an existing ID
        resumeHardx.setId(1L);

        int databaseSizeBeforeCreate = resumeHardxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeHardxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardx)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResumeHardxes() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        // Get all the resumeHardxList
        restResumeHardxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeHardx.getId().intValue())));
    }

    @Test
    @Transactional
    void getResumeHardx() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        // Get the resumeHardx
        restResumeHardxMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeHardx.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeHardx.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingResumeHardx() throws Exception {
        // Get the resumeHardx
        restResumeHardxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeHardx() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();

        // Update the resumeHardx
        ResumeHardx updatedResumeHardx = resumeHardxRepository.findById(resumeHardx.getId()).get();
        // Disconnect from session so that the updates on updatedResumeHardx are not directly saved in db
        em.detach(updatedResumeHardx);

        restResumeHardxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeHardx.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeHardx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardx testResumeHardx = resumeHardxList.get(resumeHardxList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeHardx.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeHardx)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeHardxWithPatch() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();

        // Update the resumeHardx using partial update
        ResumeHardx partialUpdatedResumeHardx = new ResumeHardx();
        partialUpdatedResumeHardx.setId(resumeHardx.getId());

        restResumeHardxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeHardx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeHardx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardx testResumeHardx = resumeHardxList.get(resumeHardxList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateResumeHardxWithPatch() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();

        // Update the resumeHardx using partial update
        ResumeHardx partialUpdatedResumeHardx = new ResumeHardx();
        partialUpdatedResumeHardx.setId(resumeHardx.getId());

        restResumeHardxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeHardx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeHardx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
        ResumeHardx testResumeHardx = resumeHardxList.get(resumeHardxList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeHardx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeHardx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeHardx() throws Exception {
        int databaseSizeBeforeUpdate = resumeHardxRepository.findAll().size();
        resumeHardx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeHardxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeHardx))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeHardx in the database
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeHardx() throws Exception {
        // Initialize the database
        resumeHardxRepository.saveAndFlush(resumeHardx);

        int databaseSizeBeforeDelete = resumeHardxRepository.findAll().size();

        // Delete the resumeHardx
        restResumeHardxMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeHardx.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeHardx> resumeHardxList = resumeHardxRepository.findAll();
        assertThat(resumeHardxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
