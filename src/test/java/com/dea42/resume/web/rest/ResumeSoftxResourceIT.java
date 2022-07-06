package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeSoftx;
import com.dea42.resume.repository.ResumeSoftxRepository;
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
 * Integration tests for the {@link ResumeSoftxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeSoftxResourceIT {

    private static final String ENTITY_API_URL = "/api/resume-softxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeSoftxRepository resumeSoftxRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeSoftxMockMvc;

    private ResumeSoftx resumeSoftx;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSoftx createEntity(EntityManager em) {
        ResumeSoftx resumeSoftx = new ResumeSoftx();
        return resumeSoftx;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSoftx createUpdatedEntity(EntityManager em) {
        ResumeSoftx resumeSoftx = new ResumeSoftx();
        return resumeSoftx;
    }

    @BeforeEach
    public void initTest() {
        resumeSoftx = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeSoftx() throws Exception {
        int databaseSizeBeforeCreate = resumeSoftxRepository.findAll().size();
        // Create the ResumeSoftx
        restResumeSoftxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftx)))
            .andExpect(status().isCreated());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeSoftx testResumeSoftx = resumeSoftxList.get(resumeSoftxList.size() - 1);
    }

    @Test
    @Transactional
    void createResumeSoftxWithExistingId() throws Exception {
        // Create the ResumeSoftx with an existing ID
        resumeSoftx.setId(1L);

        int databaseSizeBeforeCreate = resumeSoftxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeSoftxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftx)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResumeSoftxes() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        // Get all the resumeSoftxList
        restResumeSoftxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeSoftx.getId().intValue())));
    }

    @Test
    @Transactional
    void getResumeSoftx() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        // Get the resumeSoftx
        restResumeSoftxMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeSoftx.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeSoftx.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingResumeSoftx() throws Exception {
        // Get the resumeSoftx
        restResumeSoftxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeSoftx() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();

        // Update the resumeSoftx
        ResumeSoftx updatedResumeSoftx = resumeSoftxRepository.findById(resumeSoftx.getId()).get();
        // Disconnect from session so that the updates on updatedResumeSoftx are not directly saved in db
        em.detach(updatedResumeSoftx);

        restResumeSoftxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeSoftx.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeSoftx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftx testResumeSoftx = resumeSoftxList.get(resumeSoftxList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeSoftx.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftx)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeSoftxWithPatch() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();

        // Update the resumeSoftx using partial update
        ResumeSoftx partialUpdatedResumeSoftx = new ResumeSoftx();
        partialUpdatedResumeSoftx.setId(resumeSoftx.getId());

        restResumeSoftxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSoftx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSoftx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftx testResumeSoftx = resumeSoftxList.get(resumeSoftxList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateResumeSoftxWithPatch() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();

        // Update the resumeSoftx using partial update
        ResumeSoftx partialUpdatedResumeSoftx = new ResumeSoftx();
        partialUpdatedResumeSoftx.setId(resumeSoftx.getId());

        restResumeSoftxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSoftx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSoftx))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftx testResumeSoftx = resumeSoftxList.get(resumeSoftxList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeSoftx.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftx))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeSoftx() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftxRepository.findAll().size();
        resumeSoftx.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeSoftx))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSoftx in the database
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeSoftx() throws Exception {
        // Initialize the database
        resumeSoftxRepository.saveAndFlush(resumeSoftx);

        int databaseSizeBeforeDelete = resumeSoftxRepository.findAll().size();

        // Delete the resumeSoftx
        restResumeSoftxMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeSoftx.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeSoftx> resumeSoftxList = resumeSoftxRepository.findAll();
        assertThat(resumeSoftxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
