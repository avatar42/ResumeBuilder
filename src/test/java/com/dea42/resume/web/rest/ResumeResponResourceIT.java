package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeRespon;
import com.dea42.resume.repository.ResumeResponRepository;
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
 * Integration tests for the {@link ResumeResponResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeResponResourceIT {

    private static final Integer DEFAULT_JOB_ID = 1;
    private static final Integer UPDATED_JOB_ID = 2;

    private static final Integer DEFAULT_RESPON_ORDER = 1;
    private static final Integer UPDATED_RESPON_ORDER = 2;

    private static final Integer DEFAULT_RESPON_SHOW = 1;
    private static final Integer UPDATED_RESPON_SHOW = 2;

    private static final String ENTITY_API_URL = "/api/resume-respons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeResponRepository resumeResponRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeResponMockMvc;

    private ResumeRespon resumeRespon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeRespon createEntity(EntityManager em) {
        ResumeRespon resumeRespon = new ResumeRespon()
            .jobId(DEFAULT_JOB_ID)
            .responOrder(DEFAULT_RESPON_ORDER)
            .responShow(DEFAULT_RESPON_SHOW);
        return resumeRespon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeRespon createUpdatedEntity(EntityManager em) {
        ResumeRespon resumeRespon = new ResumeRespon()
            .jobId(UPDATED_JOB_ID)
            .responOrder(UPDATED_RESPON_ORDER)
            .responShow(UPDATED_RESPON_SHOW);
        return resumeRespon;
    }

    @BeforeEach
    public void initTest() {
        resumeRespon = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeRespon() throws Exception {
        int databaseSizeBeforeCreate = resumeResponRepository.findAll().size();
        // Create the ResumeRespon
        restResumeResponMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isCreated());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeRespon testResumeRespon = resumeResponList.get(resumeResponList.size() - 1);
        assertThat(testResumeRespon.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testResumeRespon.getResponOrder()).isEqualTo(DEFAULT_RESPON_ORDER);
        assertThat(testResumeRespon.getResponShow()).isEqualTo(DEFAULT_RESPON_SHOW);
    }

    @Test
    @Transactional
    void createResumeResponWithExistingId() throws Exception {
        // Create the ResumeRespon with an existing ID
        resumeRespon.setId(1L);

        int databaseSizeBeforeCreate = resumeResponRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeResponMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeResponRepository.findAll().size();
        // set the field null
        resumeRespon.setJobId(null);

        // Create the ResumeRespon, which fails.

        restResumeResponMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isBadRequest());

        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeResponRepository.findAll().size();
        // set the field null
        resumeRespon.setResponOrder(null);

        // Create the ResumeRespon, which fails.

        restResumeResponMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isBadRequest());

        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponShowIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeResponRepository.findAll().size();
        // set the field null
        resumeRespon.setResponShow(null);

        // Create the ResumeRespon, which fails.

        restResumeResponMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isBadRequest());

        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumeRespons() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        // Get all the resumeResponList
        restResumeResponMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeRespon.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].responOrder").value(hasItem(DEFAULT_RESPON_ORDER)))
            .andExpect(jsonPath("$.[*].responShow").value(hasItem(DEFAULT_RESPON_SHOW)));
    }

    @Test
    @Transactional
    void getResumeRespon() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        // Get the resumeRespon
        restResumeResponMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeRespon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeRespon.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID))
            .andExpect(jsonPath("$.responOrder").value(DEFAULT_RESPON_ORDER))
            .andExpect(jsonPath("$.responShow").value(DEFAULT_RESPON_SHOW));
    }

    @Test
    @Transactional
    void getNonExistingResumeRespon() throws Exception {
        // Get the resumeRespon
        restResumeResponMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeRespon() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();

        // Update the resumeRespon
        ResumeRespon updatedResumeRespon = resumeResponRepository.findById(resumeRespon.getId()).get();
        // Disconnect from session so that the updates on updatedResumeRespon are not directly saved in db
        em.detach(updatedResumeRespon);
        updatedResumeRespon.jobId(UPDATED_JOB_ID).responOrder(UPDATED_RESPON_ORDER).responShow(UPDATED_RESPON_SHOW);

        restResumeResponMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeRespon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeRespon))
            )
            .andExpect(status().isOk());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
        ResumeRespon testResumeRespon = resumeResponList.get(resumeResponList.size() - 1);
        assertThat(testResumeRespon.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testResumeRespon.getResponOrder()).isEqualTo(UPDATED_RESPON_ORDER);
        assertThat(testResumeRespon.getResponShow()).isEqualTo(UPDATED_RESPON_SHOW);
    }

    @Test
    @Transactional
    void putNonExistingResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeRespon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeRespon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeRespon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeRespon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeResponWithPatch() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();

        // Update the resumeRespon using partial update
        ResumeRespon partialUpdatedResumeRespon = new ResumeRespon();
        partialUpdatedResumeRespon.setId(resumeRespon.getId());

        partialUpdatedResumeRespon.jobId(UPDATED_JOB_ID).responOrder(UPDATED_RESPON_ORDER).responShow(UPDATED_RESPON_SHOW);

        restResumeResponMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeRespon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeRespon))
            )
            .andExpect(status().isOk());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
        ResumeRespon testResumeRespon = resumeResponList.get(resumeResponList.size() - 1);
        assertThat(testResumeRespon.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testResumeRespon.getResponOrder()).isEqualTo(UPDATED_RESPON_ORDER);
        assertThat(testResumeRespon.getResponShow()).isEqualTo(UPDATED_RESPON_SHOW);
    }

    @Test
    @Transactional
    void fullUpdateResumeResponWithPatch() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();

        // Update the resumeRespon using partial update
        ResumeRespon partialUpdatedResumeRespon = new ResumeRespon();
        partialUpdatedResumeRespon.setId(resumeRespon.getId());

        partialUpdatedResumeRespon.jobId(UPDATED_JOB_ID).responOrder(UPDATED_RESPON_ORDER).responShow(UPDATED_RESPON_SHOW);

        restResumeResponMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeRespon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeRespon))
            )
            .andExpect(status().isOk());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
        ResumeRespon testResumeRespon = resumeResponList.get(resumeResponList.size() - 1);
        assertThat(testResumeRespon.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testResumeRespon.getResponOrder()).isEqualTo(UPDATED_RESPON_ORDER);
        assertThat(testResumeRespon.getResponShow()).isEqualTo(UPDATED_RESPON_SHOW);
    }

    @Test
    @Transactional
    void patchNonExistingResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeRespon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeRespon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeRespon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeRespon() throws Exception {
        int databaseSizeBeforeUpdate = resumeResponRepository.findAll().size();
        resumeRespon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeResponMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeRespon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeRespon in the database
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeRespon() throws Exception {
        // Initialize the database
        resumeResponRepository.saveAndFlush(resumeRespon);

        int databaseSizeBeforeDelete = resumeResponRepository.findAll().size();

        // Delete the resumeRespon
        restResumeResponMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeRespon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeRespon> resumeResponList = resumeResponRepository.findAll();
        assertThat(resumeResponList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
