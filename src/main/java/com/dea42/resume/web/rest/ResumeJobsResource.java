package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeJobs;
import com.dea42.resume.repository.ResumeJobsRepository;
import com.dea42.resume.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dea42.resume.domain.ResumeJobs}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeJobsResource {

    private final Logger log = LoggerFactory.getLogger(ResumeJobsResource.class);

    private static final String ENTITY_NAME = "resumeJobs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeJobsRepository resumeJobsRepository;

    public ResumeJobsResource(ResumeJobsRepository resumeJobsRepository) {
        this.resumeJobsRepository = resumeJobsRepository;
    }

    /**
     * {@code POST  /resume-jobs} : Create a new resumeJobs.
     *
     * @param resumeJobs the resumeJobs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeJobs, or with status {@code 400 (Bad Request)} if the resumeJobs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-jobs")
    public ResponseEntity<ResumeJobs> createResumeJobs(@Valid @RequestBody ResumeJobs resumeJobs) throws URISyntaxException {
        log.debug("REST request to save ResumeJobs : {}", resumeJobs);
        if (resumeJobs.getId() != null) {
            throw new BadRequestAlertException("A new resumeJobs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeJobs result = resumeJobsRepository.save(resumeJobs);
        return ResponseEntity
            .created(new URI("/api/resume-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-jobs/:id} : Updates an existing resumeJobs.
     *
     * @param id the id of the resumeJobs to save.
     * @param resumeJobs the resumeJobs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeJobs,
     * or with status {@code 400 (Bad Request)} if the resumeJobs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeJobs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-jobs/{id}")
    public ResponseEntity<ResumeJobs> updateResumeJobs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumeJobs resumeJobs
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeJobs : {}, {}", id, resumeJobs);
        if (resumeJobs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeJobs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeJobsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeJobs result = resumeJobsRepository.save(resumeJobs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeJobs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-jobs/:id} : Partial updates given fields of an existing resumeJobs, field will ignore if it is null
     *
     * @param id the id of the resumeJobs to save.
     * @param resumeJobs the resumeJobs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeJobs,
     * or with status {@code 400 (Bad Request)} if the resumeJobs is not valid,
     * or with status {@code 404 (Not Found)} if the resumeJobs is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeJobs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-jobs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeJobs> partialUpdateResumeJobs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumeJobs resumeJobs
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeJobs partially : {}, {}", id, resumeJobs);
        if (resumeJobs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeJobs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeJobsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeJobs> result = resumeJobsRepository
            .findById(resumeJobs.getId())
            .map(existingResumeJobs -> {
                if (resumeJobs.getStartDate() != null) {
                    existingResumeJobs.setStartDate(resumeJobs.getStartDate());
                }
                if (resumeJobs.getEndDate() != null) {
                    existingResumeJobs.setEndDate(resumeJobs.getEndDate());
                }
                if (resumeJobs.getCompany() != null) {
                    existingResumeJobs.setCompany(resumeJobs.getCompany());
                }
                if (resumeJobs.getWorkStreet() != null) {
                    existingResumeJobs.setWorkStreet(resumeJobs.getWorkStreet());
                }
                if (resumeJobs.getLocation() != null) {
                    existingResumeJobs.setLocation(resumeJobs.getLocation());
                }
                if (resumeJobs.getWorkZip() != null) {
                    existingResumeJobs.setWorkZip(resumeJobs.getWorkZip());
                }
                if (resumeJobs.getTitle() != null) {
                    existingResumeJobs.setTitle(resumeJobs.getTitle());
                }
                if (resumeJobs.getMonths() != null) {
                    existingResumeJobs.setMonths(resumeJobs.getMonths());
                }
                if (resumeJobs.getContract() != null) {
                    existingResumeJobs.setContract(resumeJobs.getContract());
                }
                if (resumeJobs.getAgency() != null) {
                    existingResumeJobs.setAgency(resumeJobs.getAgency());
                }
                if (resumeJobs.getMgr() != null) {
                    existingResumeJobs.setMgr(resumeJobs.getMgr());
                }
                if (resumeJobs.getMgrPhone() != null) {
                    existingResumeJobs.setMgrPhone(resumeJobs.getMgrPhone());
                }
                if (resumeJobs.getPay() != null) {
                    existingResumeJobs.setPay(resumeJobs.getPay());
                }
                if (resumeJobs.getCorpAddr() != null) {
                    existingResumeJobs.setCorpAddr(resumeJobs.getCorpAddr());
                }
                if (resumeJobs.getCorpPhone() != null) {
                    existingResumeJobs.setCorpPhone(resumeJobs.getCorpPhone());
                }
                if (resumeJobs.getAgencyAddr() != null) {
                    existingResumeJobs.setAgencyAddr(resumeJobs.getAgencyAddr());
                }
                if (resumeJobs.getAgencyPhone() != null) {
                    existingResumeJobs.setAgencyPhone(resumeJobs.getAgencyPhone());
                }

                return existingResumeJobs;
            })
            .map(resumeJobsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeJobs.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-jobs} : get all the resumeJobs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeJobs in body.
     */
    @GetMapping("/resume-jobs")
    public List<ResumeJobs> getAllResumeJobs() {
        log.debug("REST request to get all ResumeJobs");
        return resumeJobsRepository.findAll();
    }

    /**
     * {@code GET  /resume-jobs/:id} : get the "id" resumeJobs.
     *
     * @param id the id of the resumeJobs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeJobs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-jobs/{id}")
    public ResponseEntity<ResumeJobs> getResumeJobs(@PathVariable Long id) {
        log.debug("REST request to get ResumeJobs : {}", id);
        Optional<ResumeJobs> resumeJobs = resumeJobsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeJobs);
    }

    /**
     * {@code DELETE  /resume-jobs/:id} : delete the "id" resumeJobs.
     *
     * @param id the id of the resumeJobs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-jobs/{id}")
    public ResponseEntity<Void> deleteResumeJobs(@PathVariable Long id) {
        log.debug("REST request to delete ResumeJobs : {}", id);
        resumeJobsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
