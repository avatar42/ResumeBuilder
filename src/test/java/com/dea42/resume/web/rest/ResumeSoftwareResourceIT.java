package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeSoftware;
import com.dea42.resume.repository.ResumeSoftwareRepository;
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
 * Integration tests for the {@link ResumeSoftwareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeSoftwareResourceIT {

    private static final String DEFAULT_SOFTWARE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SOFTWARE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOFTWARE_VER = "AAAAAAAAAA";
    private static final String UPDATED_SOFTWARE_VER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOFTWARE_CAT_ID = 1;
    private static final Integer UPDATED_SOFTWARE_CAT_ID = 2;

    private static final Integer DEFAULT_SUMMARRY_CAT_ID = 1;
    private static final Integer UPDATED_SUMMARRY_CAT_ID = 2;

    private static final String ENTITY_API_URL = "/api/resume-softwares";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeSoftwareRepository resumeSoftwareRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeSoftwareMockMvc;

    private ResumeSoftware resumeSoftware;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSoftware createEntity(EntityManager em) {
        ResumeSoftware resumeSoftware = new ResumeSoftware()
            .softwareName(DEFAULT_SOFTWARE_NAME)
            .softwareVer(DEFAULT_SOFTWARE_VER)
            .softwareCatId(DEFAULT_SOFTWARE_CAT_ID)
            .summarryCatId(DEFAULT_SUMMARRY_CAT_ID);
        return resumeSoftware;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeSoftware createUpdatedEntity(EntityManager em) {
        ResumeSoftware resumeSoftware = new ResumeSoftware()
            .softwareName(UPDATED_SOFTWARE_NAME)
            .softwareVer(UPDATED_SOFTWARE_VER)
            .softwareCatId(UPDATED_SOFTWARE_CAT_ID)
            .summarryCatId(UPDATED_SUMMARRY_CAT_ID);
        return resumeSoftware;
    }

    @BeforeEach
    public void initTest() {
        resumeSoftware = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeSoftware() throws Exception {
        int databaseSizeBeforeCreate = resumeSoftwareRepository.findAll().size();
        // Create the ResumeSoftware
        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isCreated());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeSoftware testResumeSoftware = resumeSoftwareList.get(resumeSoftwareList.size() - 1);
        assertThat(testResumeSoftware.getSoftwareName()).isEqualTo(DEFAULT_SOFTWARE_NAME);
        assertThat(testResumeSoftware.getSoftwareVer()).isEqualTo(DEFAULT_SOFTWARE_VER);
        assertThat(testResumeSoftware.getSoftwareCatId()).isEqualTo(DEFAULT_SOFTWARE_CAT_ID);
        assertThat(testResumeSoftware.getSummarryCatId()).isEqualTo(DEFAULT_SUMMARRY_CAT_ID);
    }

    @Test
    @Transactional
    void createResumeSoftwareWithExistingId() throws Exception {
        // Create the ResumeSoftware with an existing ID
        resumeSoftware.setId(1L);

        int databaseSizeBeforeCreate = resumeSoftwareRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoftwareNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSoftwareRepository.findAll().size();
        // set the field null
        resumeSoftware.setSoftwareName(null);

        // Create the ResumeSoftware, which fails.

        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoftwareVerIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSoftwareRepository.findAll().size();
        // set the field null
        resumeSoftware.setSoftwareVer(null);

        // Create the ResumeSoftware, which fails.

        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoftwareCatIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSoftwareRepository.findAll().size();
        // set the field null
        resumeSoftware.setSoftwareCatId(null);

        // Create the ResumeSoftware, which fails.

        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSummarryCatIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeSoftwareRepository.findAll().size();
        // set the field null
        resumeSoftware.setSummarryCatId(null);

        // Create the ResumeSoftware, which fails.

        restResumeSoftwareMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumeSoftwares() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        // Get all the resumeSoftwareList
        restResumeSoftwareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeSoftware.getId().intValue())))
            .andExpect(jsonPath("$.[*].softwareName").value(hasItem(DEFAULT_SOFTWARE_NAME)))
            .andExpect(jsonPath("$.[*].softwareVer").value(hasItem(DEFAULT_SOFTWARE_VER)))
            .andExpect(jsonPath("$.[*].softwareCatId").value(hasItem(DEFAULT_SOFTWARE_CAT_ID)))
            .andExpect(jsonPath("$.[*].summarryCatId").value(hasItem(DEFAULT_SUMMARRY_CAT_ID)));
    }

    @Test
    @Transactional
    void getResumeSoftware() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        // Get the resumeSoftware
        restResumeSoftwareMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeSoftware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeSoftware.getId().intValue()))
            .andExpect(jsonPath("$.softwareName").value(DEFAULT_SOFTWARE_NAME))
            .andExpect(jsonPath("$.softwareVer").value(DEFAULT_SOFTWARE_VER))
            .andExpect(jsonPath("$.softwareCatId").value(DEFAULT_SOFTWARE_CAT_ID))
            .andExpect(jsonPath("$.summarryCatId").value(DEFAULT_SUMMARRY_CAT_ID));
    }

    @Test
    @Transactional
    void getNonExistingResumeSoftware() throws Exception {
        // Get the resumeSoftware
        restResumeSoftwareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeSoftware() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();

        // Update the resumeSoftware
        ResumeSoftware updatedResumeSoftware = resumeSoftwareRepository.findById(resumeSoftware.getId()).get();
        // Disconnect from session so that the updates on updatedResumeSoftware are not directly saved in db
        em.detach(updatedResumeSoftware);
        updatedResumeSoftware
            .softwareName(UPDATED_SOFTWARE_NAME)
            .softwareVer(UPDATED_SOFTWARE_VER)
            .softwareCatId(UPDATED_SOFTWARE_CAT_ID)
            .summarryCatId(UPDATED_SUMMARRY_CAT_ID);

        restResumeSoftwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeSoftware.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeSoftware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftware testResumeSoftware = resumeSoftwareList.get(resumeSoftwareList.size() - 1);
        assertThat(testResumeSoftware.getSoftwareName()).isEqualTo(UPDATED_SOFTWARE_NAME);
        assertThat(testResumeSoftware.getSoftwareVer()).isEqualTo(UPDATED_SOFTWARE_VER);
        assertThat(testResumeSoftware.getSoftwareCatId()).isEqualTo(UPDATED_SOFTWARE_CAT_ID);
        assertThat(testResumeSoftware.getSummarryCatId()).isEqualTo(UPDATED_SUMMARRY_CAT_ID);
    }

    @Test
    @Transactional
    void putNonExistingResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeSoftware.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeSoftware)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeSoftwareWithPatch() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();

        // Update the resumeSoftware using partial update
        ResumeSoftware partialUpdatedResumeSoftware = new ResumeSoftware();
        partialUpdatedResumeSoftware.setId(resumeSoftware.getId());

        partialUpdatedResumeSoftware
            .softwareVer(UPDATED_SOFTWARE_VER)
            .softwareCatId(UPDATED_SOFTWARE_CAT_ID)
            .summarryCatId(UPDATED_SUMMARRY_CAT_ID);

        restResumeSoftwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSoftware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSoftware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftware testResumeSoftware = resumeSoftwareList.get(resumeSoftwareList.size() - 1);
        assertThat(testResumeSoftware.getSoftwareName()).isEqualTo(DEFAULT_SOFTWARE_NAME);
        assertThat(testResumeSoftware.getSoftwareVer()).isEqualTo(UPDATED_SOFTWARE_VER);
        assertThat(testResumeSoftware.getSoftwareCatId()).isEqualTo(UPDATED_SOFTWARE_CAT_ID);
        assertThat(testResumeSoftware.getSummarryCatId()).isEqualTo(UPDATED_SUMMARRY_CAT_ID);
    }

    @Test
    @Transactional
    void fullUpdateResumeSoftwareWithPatch() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();

        // Update the resumeSoftware using partial update
        ResumeSoftware partialUpdatedResumeSoftware = new ResumeSoftware();
        partialUpdatedResumeSoftware.setId(resumeSoftware.getId());

        partialUpdatedResumeSoftware
            .softwareName(UPDATED_SOFTWARE_NAME)
            .softwareVer(UPDATED_SOFTWARE_VER)
            .softwareCatId(UPDATED_SOFTWARE_CAT_ID)
            .summarryCatId(UPDATED_SUMMARRY_CAT_ID);

        restResumeSoftwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeSoftware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeSoftware))
            )
            .andExpect(status().isOk());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
        ResumeSoftware testResumeSoftware = resumeSoftwareList.get(resumeSoftwareList.size() - 1);
        assertThat(testResumeSoftware.getSoftwareName()).isEqualTo(UPDATED_SOFTWARE_NAME);
        assertThat(testResumeSoftware.getSoftwareVer()).isEqualTo(UPDATED_SOFTWARE_VER);
        assertThat(testResumeSoftware.getSoftwareCatId()).isEqualTo(UPDATED_SOFTWARE_CAT_ID);
        assertThat(testResumeSoftware.getSummarryCatId()).isEqualTo(UPDATED_SUMMARRY_CAT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeSoftware.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeSoftware() throws Exception {
        int databaseSizeBeforeUpdate = resumeSoftwareRepository.findAll().size();
        resumeSoftware.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeSoftwareMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeSoftware))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeSoftware in the database
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeSoftware() throws Exception {
        // Initialize the database
        resumeSoftwareRepository.saveAndFlush(resumeSoftware);

        int databaseSizeBeforeDelete = resumeSoftwareRepository.findAll().size();

        // Delete the resumeSoftware
        restResumeSoftwareMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeSoftware.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeSoftware> resumeSoftwareList = resumeSoftwareRepository.findAll();
        assertThat(resumeSoftwareList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
