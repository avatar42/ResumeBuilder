package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeSummary;
import com.dea42.resume.repository.ResumeSummaryRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeSummary}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeSummaryResource {

    private final Logger log = LoggerFactory.getLogger(ResumeSummaryResource.class);

    private static final String ENTITY_NAME = "resumeSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeSummaryRepository resumeSummaryRepository;

    public ResumeSummaryResource(ResumeSummaryRepository resumeSummaryRepository) {
        this.resumeSummaryRepository = resumeSummaryRepository;
    }

    /**
     * {@code POST  /resume-summaries} : Create a new resumeSummary.
     *
     * @param resumeSummary the resumeSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeSummary, or with status {@code 400 (Bad Request)} if the resumeSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-summaries")
    public ResponseEntity<ResumeSummary> createResumeSummary(@Valid @RequestBody ResumeSummary resumeSummary) throws URISyntaxException {
        log.debug("REST request to save ResumeSummary : {}", resumeSummary);
        if (resumeSummary.getId() != null) {
            throw new BadRequestAlertException("A new resumeSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeSummary result = resumeSummaryRepository.save(resumeSummary);
        return ResponseEntity
            .created(new URI("/api/resume-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-summaries/:id} : Updates an existing resumeSummary.
     *
     * @param id the id of the resumeSummary to save.
     * @param resumeSummary the resumeSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSummary,
     * or with status {@code 400 (Bad Request)} if the resumeSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-summaries/{id}")
    public ResponseEntity<ResumeSummary> updateResumeSummary(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumeSummary resumeSummary
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeSummary : {}, {}", id, resumeSummary);
        if (resumeSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeSummary result = resumeSummaryRepository.save(resumeSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-summaries/:id} : Partial updates given fields of an existing resumeSummary, field will ignore if it is null
     *
     * @param id the id of the resumeSummary to save.
     * @param resumeSummary the resumeSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSummary,
     * or with status {@code 400 (Bad Request)} if the resumeSummary is not valid,
     * or with status {@code 404 (Not Found)} if the resumeSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeSummary> partialUpdateResumeSummary(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumeSummary resumeSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeSummary partially : {}, {}", id, resumeSummary);
        if (resumeSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeSummary> result = resumeSummaryRepository
            .findById(resumeSummary.getId())
            .map(existingResumeSummary -> {
                if (resumeSummary.getSummaryName() != null) {
                    existingResumeSummary.setSummaryName(resumeSummary.getSummaryName());
                }
                if (resumeSummary.getSummaryOrder() != null) {
                    existingResumeSummary.setSummaryOrder(resumeSummary.getSummaryOrder());
                }

                return existingResumeSummary;
            })
            .map(resumeSummaryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-summaries} : get all the resumeSummaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeSummaries in body.
     */
    @GetMapping("/resume-summaries")
    public List<ResumeSummary> getAllResumeSummaries() {
        log.debug("REST request to get all ResumeSummaries");
        return resumeSummaryRepository.findAll();
    }

    /**
     * {@code GET  /resume-summaries/:id} : get the "id" resumeSummary.
     *
     * @param id the id of the resumeSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-summaries/{id}")
    public ResponseEntity<ResumeSummary> getResumeSummary(@PathVariable Long id) {
        log.debug("REST request to get ResumeSummary : {}", id);
        Optional<ResumeSummary> resumeSummary = resumeSummaryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeSummary);
    }

    /**
     * {@code DELETE  /resume-summaries/:id} : delete the "id" resumeSummary.
     *
     * @param id the id of the resumeSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-summaries/{id}")
    public ResponseEntity<Void> deleteResumeSummary(@PathVariable Long id) {
        log.debug("REST request to delete ResumeSummary : {}", id);
        resumeSummaryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
