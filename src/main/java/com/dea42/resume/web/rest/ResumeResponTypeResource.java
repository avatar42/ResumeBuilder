package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeResponType;
import com.dea42.resume.repository.ResumeResponTypeRepository;
import com.dea42.resume.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dea42.resume.domain.ResumeResponType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeResponTypeResource {

    private final Logger log = LoggerFactory.getLogger(ResumeResponTypeResource.class);

    private static final String ENTITY_NAME = "resumeResponType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeResponTypeRepository resumeResponTypeRepository;

    public ResumeResponTypeResource(ResumeResponTypeRepository resumeResponTypeRepository) {
        this.resumeResponTypeRepository = resumeResponTypeRepository;
    }

    /**
     * {@code POST  /resume-respon-types} : Create a new resumeResponType.
     *
     * @param resumeResponType the resumeResponType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeResponType, or with status {@code 400 (Bad Request)} if the resumeResponType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-respon-types")
    public ResponseEntity<ResumeResponType> createResumeResponType(@RequestBody ResumeResponType resumeResponType)
        throws URISyntaxException {
        log.debug("REST request to save ResumeResponType : {}", resumeResponType);
        if (resumeResponType.getId() != null) {
            throw new BadRequestAlertException("A new resumeResponType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeResponType result = resumeResponTypeRepository.save(resumeResponType);
        return ResponseEntity
            .created(new URI("/api/resume-respon-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-respon-types/:id} : Updates an existing resumeResponType.
     *
     * @param id the id of the resumeResponType to save.
     * @param resumeResponType the resumeResponType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeResponType,
     * or with status {@code 400 (Bad Request)} if the resumeResponType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeResponType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-respon-types/{id}")
    public ResponseEntity<ResumeResponType> updateResumeResponType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeResponType resumeResponType
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeResponType : {}, {}", id, resumeResponType);
        if (resumeResponType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeResponType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeResponTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeResponType result = resumeResponTypeRepository.save(resumeResponType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeResponType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-respon-types/:id} : Partial updates given fields of an existing resumeResponType, field will ignore if it is null
     *
     * @param id the id of the resumeResponType to save.
     * @param resumeResponType the resumeResponType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeResponType,
     * or with status {@code 400 (Bad Request)} if the resumeResponType is not valid,
     * or with status {@code 404 (Not Found)} if the resumeResponType is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeResponType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-respon-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeResponType> partialUpdateResumeResponType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeResponType resumeResponType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeResponType partially : {}, {}", id, resumeResponType);
        if (resumeResponType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeResponType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeResponTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeResponType> result = resumeResponTypeRepository
            .findById(resumeResponType.getId())
            .map(existingResumeResponType -> {
                if (resumeResponType.getDescription() != null) {
                    existingResumeResponType.setDescription(resumeResponType.getDescription());
                }

                return existingResumeResponType;
            })
            .map(resumeResponTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeResponType.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-respon-types} : get all the resumeResponTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeResponTypes in body.
     */
    @GetMapping("/resume-respon-types")
    public List<ResumeResponType> getAllResumeResponTypes() {
        log.debug("REST request to get all ResumeResponTypes");
        return resumeResponTypeRepository.findAll();
    }

    /**
     * {@code GET  /resume-respon-types/:id} : get the "id" resumeResponType.
     *
     * @param id the id of the resumeResponType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeResponType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-respon-types/{id}")
    public ResponseEntity<ResumeResponType> getResumeResponType(@PathVariable Long id) {
        log.debug("REST request to get ResumeResponType : {}", id);
        Optional<ResumeResponType> resumeResponType = resumeResponTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeResponType);
    }

    /**
     * {@code DELETE  /resume-respon-types/:id} : delete the "id" resumeResponType.
     *
     * @param id the id of the resumeResponType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-respon-types/{id}")
    public ResponseEntity<Void> deleteResumeResponType(@PathVariable Long id) {
        log.debug("REST request to delete ResumeResponType : {}", id);
        resumeResponTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
