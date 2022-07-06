package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeHardware;
import com.dea42.resume.repository.ResumeHardwareRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeHardware}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeHardwareResource {

    private final Logger log = LoggerFactory.getLogger(ResumeHardwareResource.class);

    private static final String ENTITY_NAME = "resumeHardware";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeHardwareRepository resumeHardwareRepository;

    public ResumeHardwareResource(ResumeHardwareRepository resumeHardwareRepository) {
        this.resumeHardwareRepository = resumeHardwareRepository;
    }

    /**
     * {@code POST  /resume-hardwares} : Create a new resumeHardware.
     *
     * @param resumeHardware the resumeHardware to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeHardware, or with status {@code 400 (Bad Request)} if the resumeHardware has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-hardwares")
    public ResponseEntity<ResumeHardware> createResumeHardware(@Valid @RequestBody ResumeHardware resumeHardware)
        throws URISyntaxException {
        log.debug("REST request to save ResumeHardware : {}", resumeHardware);
        if (resumeHardware.getId() != null) {
            throw new BadRequestAlertException("A new resumeHardware cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeHardware result = resumeHardwareRepository.save(resumeHardware);
        return ResponseEntity
            .created(new URI("/api/resume-hardwares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-hardwares/:id} : Updates an existing resumeHardware.
     *
     * @param id the id of the resumeHardware to save.
     * @param resumeHardware the resumeHardware to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeHardware,
     * or with status {@code 400 (Bad Request)} if the resumeHardware is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeHardware couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-hardwares/{id}")
    public ResponseEntity<ResumeHardware> updateResumeHardware(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumeHardware resumeHardware
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeHardware : {}, {}", id, resumeHardware);
        if (resumeHardware.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeHardware.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeHardwareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeHardware result = resumeHardwareRepository.save(resumeHardware);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeHardware.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-hardwares/:id} : Partial updates given fields of an existing resumeHardware, field will ignore if it is null
     *
     * @param id the id of the resumeHardware to save.
     * @param resumeHardware the resumeHardware to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeHardware,
     * or with status {@code 400 (Bad Request)} if the resumeHardware is not valid,
     * or with status {@code 404 (Not Found)} if the resumeHardware is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeHardware couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-hardwares/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeHardware> partialUpdateResumeHardware(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumeHardware resumeHardware
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeHardware partially : {}, {}", id, resumeHardware);
        if (resumeHardware.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeHardware.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeHardwareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeHardware> result = resumeHardwareRepository
            .findById(resumeHardware.getId())
            .map(existingResumeHardware -> {
                if (resumeHardware.getRepairCertified() != null) {
                    existingResumeHardware.setRepairCertified(resumeHardware.getRepairCertified());
                }
                if (resumeHardware.getShowSum() != null) {
                    existingResumeHardware.setShowSum(resumeHardware.getShowSum());
                }
                if (resumeHardware.getHardwareName() != null) {
                    existingResumeHardware.setHardwareName(resumeHardware.getHardwareName());
                }

                return existingResumeHardware;
            })
            .map(resumeHardwareRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeHardware.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-hardwares} : get all the resumeHardwares.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeHardwares in body.
     */
    @GetMapping("/resume-hardwares")
    public List<ResumeHardware> getAllResumeHardwares() {
        log.debug("REST request to get all ResumeHardwares");
        return resumeHardwareRepository.findAll();
    }

    /**
     * {@code GET  /resume-hardwares/:id} : get the "id" resumeHardware.
     *
     * @param id the id of the resumeHardware to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeHardware, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-hardwares/{id}")
    public ResponseEntity<ResumeHardware> getResumeHardware(@PathVariable Long id) {
        log.debug("REST request to get ResumeHardware : {}", id);
        Optional<ResumeHardware> resumeHardware = resumeHardwareRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeHardware);
    }

    /**
     * {@code DELETE  /resume-hardwares/:id} : delete the "id" resumeHardware.
     *
     * @param id the id of the resumeHardware to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-hardwares/{id}")
    public ResponseEntity<Void> deleteResumeHardware(@PathVariable Long id) {
        log.debug("REST request to delete ResumeHardware : {}", id);
        resumeHardwareRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
