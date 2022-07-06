package com.dea42.resume.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dea42.resume.IntegrationTest;
import com.dea42.resume.domain.ResumeJobs;
import com.dea42.resume.repository.ResumeJobsRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ResumeJobsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResumeJobsResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_STREET = "AAAAAAAAAA";
    private static final String UPDATED_WORK_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTHS = 1D;
    private static final Double UPDATED_MONTHS = 2D;

    private static final Integer DEFAULT_CONTRACT = 1;
    private static final Integer UPDATED_CONTRACT = 2;

    private static final String DEFAULT_AGENCY = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY = "BBBBBBBBBB";

    private static final String DEFAULT_MGR = "AAAAAAAAAA";
    private static final String UPDATED_MGR = "BBBBBBBBBB";

    private static final String DEFAULT_MGR_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MGR_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PAY = "AAAAAAAAAA";
    private static final String UPDATED_PAY = "BBBBBBBBBB";

    private static final String DEFAULT_CORP_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_CORP_ADDR = "BBBBBBBBBB";

    private static final String DEFAULT_CORP_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CORP_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_ADDR = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resume-jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumeJobsRepository resumeJobsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumeJobsMockMvc;

    private ResumeJobs resumeJobs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeJobs createEntity(EntityManager em) {
        ResumeJobs resumeJobs = new ResumeJobs()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .company(DEFAULT_COMPANY)
            .workStreet(DEFAULT_WORK_STREET)
            .location(DEFAULT_LOCATION)
            .workZip(DEFAULT_WORK_ZIP)
            .title(DEFAULT_TITLE)
            .months(DEFAULT_MONTHS)
            .contract(DEFAULT_CONTRACT)
            .agency(DEFAULT_AGENCY)
            .mgr(DEFAULT_MGR)
            .mgrPhone(DEFAULT_MGR_PHONE)
            .pay(DEFAULT_PAY)
            .corpAddr(DEFAULT_CORP_ADDR)
            .corpPhone(DEFAULT_CORP_PHONE)
            .agencyAddr(DEFAULT_AGENCY_ADDR)
            .agencyPhone(DEFAULT_AGENCY_PHONE);
        return resumeJobs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeJobs createUpdatedEntity(EntityManager em) {
        ResumeJobs resumeJobs = new ResumeJobs()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .company(UPDATED_COMPANY)
            .workStreet(UPDATED_WORK_STREET)
            .location(UPDATED_LOCATION)
            .workZip(UPDATED_WORK_ZIP)
            .title(UPDATED_TITLE)
            .months(UPDATED_MONTHS)
            .contract(UPDATED_CONTRACT)
            .agency(UPDATED_AGENCY)
            .mgr(UPDATED_MGR)
            .mgrPhone(UPDATED_MGR_PHONE)
            .pay(UPDATED_PAY)
            .corpAddr(UPDATED_CORP_ADDR)
            .corpPhone(UPDATED_CORP_PHONE)
            .agencyAddr(UPDATED_AGENCY_ADDR)
            .agencyPhone(UPDATED_AGENCY_PHONE);
        return resumeJobs;
    }

    @BeforeEach
    public void initTest() {
        resumeJobs = createEntity(em);
    }

    @Test
    @Transactional
    void createResumeJobs() throws Exception {
        int databaseSizeBeforeCreate = resumeJobsRepository.findAll().size();
        // Create the ResumeJobs
        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isCreated());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeJobs testResumeJobs = resumeJobsList.get(resumeJobsList.size() - 1);
        assertThat(testResumeJobs.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testResumeJobs.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testResumeJobs.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testResumeJobs.getWorkStreet()).isEqualTo(DEFAULT_WORK_STREET);
        assertThat(testResumeJobs.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testResumeJobs.getWorkZip()).isEqualTo(DEFAULT_WORK_ZIP);
        assertThat(testResumeJobs.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testResumeJobs.getMonths()).isEqualTo(DEFAULT_MONTHS);
        assertThat(testResumeJobs.getContract()).isEqualTo(DEFAULT_CONTRACT);
        assertThat(testResumeJobs.getAgency()).isEqualTo(DEFAULT_AGENCY);
        assertThat(testResumeJobs.getMgr()).isEqualTo(DEFAULT_MGR);
        assertThat(testResumeJobs.getMgrPhone()).isEqualTo(DEFAULT_MGR_PHONE);
        assertThat(testResumeJobs.getPay()).isEqualTo(DEFAULT_PAY);
        assertThat(testResumeJobs.getCorpAddr()).isEqualTo(DEFAULT_CORP_ADDR);
        assertThat(testResumeJobs.getCorpPhone()).isEqualTo(DEFAULT_CORP_PHONE);
        assertThat(testResumeJobs.getAgencyAddr()).isEqualTo(DEFAULT_AGENCY_ADDR);
        assertThat(testResumeJobs.getAgencyPhone()).isEqualTo(DEFAULT_AGENCY_PHONE);
    }

    @Test
    @Transactional
    void createResumeJobsWithExistingId() throws Exception {
        // Create the ResumeJobs with an existing ID
        resumeJobs.setId(1L);

        int databaseSizeBeforeCreate = resumeJobsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setStartDate(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setCompany(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setLocation(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setTitle(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContractIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setContract(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMgrIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setMgr(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgencyAddrIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeJobsRepository.findAll().size();
        // set the field null
        resumeJobs.setAgencyAddr(null);

        // Create the ResumeJobs, which fails.

        restResumeJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isBadRequest());

        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumeJobs() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        // Get all the resumeJobsList
        restResumeJobsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeJobs.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].workStreet").value(hasItem(DEFAULT_WORK_STREET)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].workZip").value(hasItem(DEFAULT_WORK_ZIP)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].agency").value(hasItem(DEFAULT_AGENCY)))
            .andExpect(jsonPath("$.[*].mgr").value(hasItem(DEFAULT_MGR)))
            .andExpect(jsonPath("$.[*].mgrPhone").value(hasItem(DEFAULT_MGR_PHONE)))
            .andExpect(jsonPath("$.[*].pay").value(hasItem(DEFAULT_PAY)))
            .andExpect(jsonPath("$.[*].corpAddr").value(hasItem(DEFAULT_CORP_ADDR)))
            .andExpect(jsonPath("$.[*].corpPhone").value(hasItem(DEFAULT_CORP_PHONE)))
            .andExpect(jsonPath("$.[*].agencyAddr").value(hasItem(DEFAULT_AGENCY_ADDR)))
            .andExpect(jsonPath("$.[*].agencyPhone").value(hasItem(DEFAULT_AGENCY_PHONE)));
    }

    @Test
    @Transactional
    void getResumeJobs() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        // Get the resumeJobs
        restResumeJobsMockMvc
            .perform(get(ENTITY_API_URL_ID, resumeJobs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumeJobs.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.workStreet").value(DEFAULT_WORK_STREET))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.workZip").value(DEFAULT_WORK_ZIP))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.months").value(DEFAULT_MONTHS.doubleValue()))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT))
            .andExpect(jsonPath("$.agency").value(DEFAULT_AGENCY))
            .andExpect(jsonPath("$.mgr").value(DEFAULT_MGR))
            .andExpect(jsonPath("$.mgrPhone").value(DEFAULT_MGR_PHONE))
            .andExpect(jsonPath("$.pay").value(DEFAULT_PAY))
            .andExpect(jsonPath("$.corpAddr").value(DEFAULT_CORP_ADDR))
            .andExpect(jsonPath("$.corpPhone").value(DEFAULT_CORP_PHONE))
            .andExpect(jsonPath("$.agencyAddr").value(DEFAULT_AGENCY_ADDR))
            .andExpect(jsonPath("$.agencyPhone").value(DEFAULT_AGENCY_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingResumeJobs() throws Exception {
        // Get the resumeJobs
        restResumeJobsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResumeJobs() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();

        // Update the resumeJobs
        ResumeJobs updatedResumeJobs = resumeJobsRepository.findById(resumeJobs.getId()).get();
        // Disconnect from session so that the updates on updatedResumeJobs are not directly saved in db
        em.detach(updatedResumeJobs);
        updatedResumeJobs
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .company(UPDATED_COMPANY)
            .workStreet(UPDATED_WORK_STREET)
            .location(UPDATED_LOCATION)
            .workZip(UPDATED_WORK_ZIP)
            .title(UPDATED_TITLE)
            .months(UPDATED_MONTHS)
            .contract(UPDATED_CONTRACT)
            .agency(UPDATED_AGENCY)
            .mgr(UPDATED_MGR)
            .mgrPhone(UPDATED_MGR_PHONE)
            .pay(UPDATED_PAY)
            .corpAddr(UPDATED_CORP_ADDR)
            .corpPhone(UPDATED_CORP_PHONE)
            .agencyAddr(UPDATED_AGENCY_ADDR)
            .agencyPhone(UPDATED_AGENCY_PHONE);

        restResumeJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResumeJobs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResumeJobs))
            )
            .andExpect(status().isOk());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
        ResumeJobs testResumeJobs = resumeJobsList.get(resumeJobsList.size() - 1);
        assertThat(testResumeJobs.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testResumeJobs.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testResumeJobs.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testResumeJobs.getWorkStreet()).isEqualTo(UPDATED_WORK_STREET);
        assertThat(testResumeJobs.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testResumeJobs.getWorkZip()).isEqualTo(UPDATED_WORK_ZIP);
        assertThat(testResumeJobs.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResumeJobs.getMonths()).isEqualTo(UPDATED_MONTHS);
        assertThat(testResumeJobs.getContract()).isEqualTo(UPDATED_CONTRACT);
        assertThat(testResumeJobs.getAgency()).isEqualTo(UPDATED_AGENCY);
        assertThat(testResumeJobs.getMgr()).isEqualTo(UPDATED_MGR);
        assertThat(testResumeJobs.getMgrPhone()).isEqualTo(UPDATED_MGR_PHONE);
        assertThat(testResumeJobs.getPay()).isEqualTo(UPDATED_PAY);
        assertThat(testResumeJobs.getCorpAddr()).isEqualTo(UPDATED_CORP_ADDR);
        assertThat(testResumeJobs.getCorpPhone()).isEqualTo(UPDATED_CORP_PHONE);
        assertThat(testResumeJobs.getAgencyAddr()).isEqualTo(UPDATED_AGENCY_ADDR);
        assertThat(testResumeJobs.getAgencyPhone()).isEqualTo(UPDATED_AGENCY_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumeJobs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeJobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumeJobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumeJobs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumeJobsWithPatch() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();

        // Update the resumeJobs using partial update
        ResumeJobs partialUpdatedResumeJobs = new ResumeJobs();
        partialUpdatedResumeJobs.setId(resumeJobs.getId());

        partialUpdatedResumeJobs
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .workZip(UPDATED_WORK_ZIP)
            .title(UPDATED_TITLE)
            .months(UPDATED_MONTHS)
            .mgr(UPDATED_MGR)
            .mgrPhone(UPDATED_MGR_PHONE)
            .corpPhone(UPDATED_CORP_PHONE)
            .agencyAddr(UPDATED_AGENCY_ADDR);

        restResumeJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeJobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeJobs))
            )
            .andExpect(status().isOk());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
        ResumeJobs testResumeJobs = resumeJobsList.get(resumeJobsList.size() - 1);
        assertThat(testResumeJobs.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testResumeJobs.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testResumeJobs.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testResumeJobs.getWorkStreet()).isEqualTo(DEFAULT_WORK_STREET);
        assertThat(testResumeJobs.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testResumeJobs.getWorkZip()).isEqualTo(UPDATED_WORK_ZIP);
        assertThat(testResumeJobs.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResumeJobs.getMonths()).isEqualTo(UPDATED_MONTHS);
        assertThat(testResumeJobs.getContract()).isEqualTo(DEFAULT_CONTRACT);
        assertThat(testResumeJobs.getAgency()).isEqualTo(DEFAULT_AGENCY);
        assertThat(testResumeJobs.getMgr()).isEqualTo(UPDATED_MGR);
        assertThat(testResumeJobs.getMgrPhone()).isEqualTo(UPDATED_MGR_PHONE);
        assertThat(testResumeJobs.getPay()).isEqualTo(DEFAULT_PAY);
        assertThat(testResumeJobs.getCorpAddr()).isEqualTo(DEFAULT_CORP_ADDR);
        assertThat(testResumeJobs.getCorpPhone()).isEqualTo(UPDATED_CORP_PHONE);
        assertThat(testResumeJobs.getAgencyAddr()).isEqualTo(UPDATED_AGENCY_ADDR);
        assertThat(testResumeJobs.getAgencyPhone()).isEqualTo(DEFAULT_AGENCY_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateResumeJobsWithPatch() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();

        // Update the resumeJobs using partial update
        ResumeJobs partialUpdatedResumeJobs = new ResumeJobs();
        partialUpdatedResumeJobs.setId(resumeJobs.getId());

        partialUpdatedResumeJobs
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .company(UPDATED_COMPANY)
            .workStreet(UPDATED_WORK_STREET)
            .location(UPDATED_LOCATION)
            .workZip(UPDATED_WORK_ZIP)
            .title(UPDATED_TITLE)
            .months(UPDATED_MONTHS)
            .contract(UPDATED_CONTRACT)
            .agency(UPDATED_AGENCY)
            .mgr(UPDATED_MGR)
            .mgrPhone(UPDATED_MGR_PHONE)
            .pay(UPDATED_PAY)
            .corpAddr(UPDATED_CORP_ADDR)
            .corpPhone(UPDATED_CORP_PHONE)
            .agencyAddr(UPDATED_AGENCY_ADDR)
            .agencyPhone(UPDATED_AGENCY_PHONE);

        restResumeJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumeJobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumeJobs))
            )
            .andExpect(status().isOk());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
        ResumeJobs testResumeJobs = resumeJobsList.get(resumeJobsList.size() - 1);
        assertThat(testResumeJobs.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testResumeJobs.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testResumeJobs.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testResumeJobs.getWorkStreet()).isEqualTo(UPDATED_WORK_STREET);
        assertThat(testResumeJobs.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testResumeJobs.getWorkZip()).isEqualTo(UPDATED_WORK_ZIP);
        assertThat(testResumeJobs.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResumeJobs.getMonths()).isEqualTo(UPDATED_MONTHS);
        assertThat(testResumeJobs.getContract()).isEqualTo(UPDATED_CONTRACT);
        assertThat(testResumeJobs.getAgency()).isEqualTo(UPDATED_AGENCY);
        assertThat(testResumeJobs.getMgr()).isEqualTo(UPDATED_MGR);
        assertThat(testResumeJobs.getMgrPhone()).isEqualTo(UPDATED_MGR_PHONE);
        assertThat(testResumeJobs.getPay()).isEqualTo(UPDATED_PAY);
        assertThat(testResumeJobs.getCorpAddr()).isEqualTo(UPDATED_CORP_ADDR);
        assertThat(testResumeJobs.getCorpPhone()).isEqualTo(UPDATED_CORP_PHONE);
        assertThat(testResumeJobs.getAgencyAddr()).isEqualTo(UPDATED_AGENCY_ADDR);
        assertThat(testResumeJobs.getAgencyPhone()).isEqualTo(UPDATED_AGENCY_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumeJobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeJobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumeJobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumeJobs() throws Exception {
        int databaseSizeBeforeUpdate = resumeJobsRepository.findAll().size();
        resumeJobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumeJobsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resumeJobs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumeJobs in the database
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumeJobs() throws Exception {
        // Initialize the database
        resumeJobsRepository.saveAndFlush(resumeJobs);

        int databaseSizeBeforeDelete = resumeJobsRepository.findAll().size();

        // Delete the resumeJobs
        restResumeJobsMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumeJobs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumeJobs> resumeJobsList = resumeJobsRepository.findAll();
        assertThat(resumeJobsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
