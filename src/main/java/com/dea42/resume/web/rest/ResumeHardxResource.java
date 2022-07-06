package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeHardx;
import com.dea42.resume.repository.ResumeHardxRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeHardx}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeHardxResource {

    private final Logger log = LoggerFactory.getLogger(ResumeHardxResource.class);

    private static final String ENTITY_NAME = "resumeHardx";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeHardxRepository resumeHardxRepository;

    public ResumeHardxResource(ResumeHardxRepository resumeHardxRepository) {
        this.resumeHardxRepository = resumeHardxRepository;
    }

    /**
     * {@code POST  /resume-hardxes} : Create a new resumeHardx.
     *
     * @param resumeHardx the resumeHardx to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeHardx, or with status {@code 400 (Bad Request)} if the resumeHardx has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-hardxes")
    public ResponseEntity<ResumeHardx> createResumeHardx(@RequestBody ResumeHardx resumeHardx) throws URISyntaxException {
        log.debug("REST request to save ResumeHardx : {}", resumeHardx);
        if (resumeHardx.getId() != null) {
            throw new BadRequestAlertException("A new resumeHardx cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeHardx result = resumeHardxRepository.save(resumeHardx);
        return ResponseEntity
            .created(new URI("/api/resume-hardxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-hardxes/:id} : Updates an existing resumeHardx.
     *
     * @param id the id of the resumeHardx to save.
     * @param resumeHardx the resumeHardx to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeHardx,
     * or with status {@code 400 (Bad Request)} if the resumeHardx is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeHardx couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-hardxes/{id}")
    public ResponseEntity<ResumeHardx> updateResumeHardx(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeHardx resumeHardx
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeHardx : {}, {}", id, resumeHardx);
        if (resumeHardx.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeHardx.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeHardxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // no save call needed as we have no fields that can be updated
        ResumeHardx result = resumeHardx;
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeHardx.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-hardxes/:id} : Partial updates given fields of an existing resumeHardx, field will ignore if it is null
     *
     * @param id the id of the resumeHardx to save.
     * @param resumeHardx the resumeHardx to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeHardx,
     * or with status {@code 400 (Bad Request)} if the resumeHardx is not valid,
     * or with status {@code 404 (Not Found)} if the resumeHardx is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeHardx couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-hardxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeHardx> partialUpdateResumeHardx(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeHardx resumeHardx
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeHardx partially : {}, {}", id, resumeHardx);
        if (resumeHardx.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeHardx.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeHardxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeHardx> result = resumeHardxRepository
            .findById(resumeHardx.getId())
            .map(existingResumeHardx -> {
                return existingResumeHardx;
            }); // .map(resumeHardxRepository::save)

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeHardx.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-hardxes} : get all the resumeHardxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeHardxes in body.
     */
    @GetMapping("/resume-hardxes")
    public List<ResumeHardx> getAllResumeHardxes() {
        log.debug("REST request to get all ResumeHardxes");
        return resumeHardxRepository.findAll();
    }

    /**
     * {@code GET  /resume-hardxes/:id} : get the "id" resumeHardx.
     *
     * @param id the id of the resumeHardx to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeHardx, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-hardxes/{id}")
    public ResponseEntity<ResumeHardx> getResumeHardx(@PathVariable Long id) {
        log.debug("REST request to get ResumeHardx : {}", id);
        Optional<ResumeHardx> resumeHardx = resumeHardxRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeHardx);
    }

    /**
     * {@code DELETE  /resume-hardxes/:id} : delete the "id" resumeHardx.
     *
     * @param id the id of the resumeHardx to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-hardxes/{id}")
    public ResponseEntity<Void> deleteResumeHardx(@PathVariable Long id) {
        log.debug("REST request to delete ResumeHardx : {}", id);
        resumeHardxRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
