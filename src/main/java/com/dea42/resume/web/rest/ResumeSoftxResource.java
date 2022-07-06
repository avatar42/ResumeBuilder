package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeSoftx;
import com.dea42.resume.repository.ResumeSoftxRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeSoftx}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeSoftxResource {

    private final Logger log = LoggerFactory.getLogger(ResumeSoftxResource.class);

    private static final String ENTITY_NAME = "resumeSoftx";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeSoftxRepository resumeSoftxRepository;

    public ResumeSoftxResource(ResumeSoftxRepository resumeSoftxRepository) {
        this.resumeSoftxRepository = resumeSoftxRepository;
    }

    /**
     * {@code POST  /resume-softxes} : Create a new resumeSoftx.
     *
     * @param resumeSoftx the resumeSoftx to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeSoftx, or with status {@code 400 (Bad Request)} if the resumeSoftx has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-softxes")
    public ResponseEntity<ResumeSoftx> createResumeSoftx(@RequestBody ResumeSoftx resumeSoftx) throws URISyntaxException {
        log.debug("REST request to save ResumeSoftx : {}", resumeSoftx);
        if (resumeSoftx.getId() != null) {
            throw new BadRequestAlertException("A new resumeSoftx cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeSoftx result = resumeSoftxRepository.save(resumeSoftx);
        return ResponseEntity
            .created(new URI("/api/resume-softxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-softxes/:id} : Updates an existing resumeSoftx.
     *
     * @param id the id of the resumeSoftx to save.
     * @param resumeSoftx the resumeSoftx to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSoftx,
     * or with status {@code 400 (Bad Request)} if the resumeSoftx is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeSoftx couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-softxes/{id}")
    public ResponseEntity<ResumeSoftx> updateResumeSoftx(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeSoftx resumeSoftx
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeSoftx : {}, {}", id, resumeSoftx);
        if (resumeSoftx.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSoftx.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSoftxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // no save call needed as we have no fields that can be updated
        ResumeSoftx result = resumeSoftx;
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSoftx.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-softxes/:id} : Partial updates given fields of an existing resumeSoftx, field will ignore if it is null
     *
     * @param id the id of the resumeSoftx to save.
     * @param resumeSoftx the resumeSoftx to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSoftx,
     * or with status {@code 400 (Bad Request)} if the resumeSoftx is not valid,
     * or with status {@code 404 (Not Found)} if the resumeSoftx is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeSoftx couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-softxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeSoftx> partialUpdateResumeSoftx(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResumeSoftx resumeSoftx
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeSoftx partially : {}, {}", id, resumeSoftx);
        if (resumeSoftx.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSoftx.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSoftxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeSoftx> result = resumeSoftxRepository
            .findById(resumeSoftx.getId())
            .map(existingResumeSoftx -> {
                return existingResumeSoftx;
            }); // .map(resumeSoftxRepository::save)

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSoftx.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-softxes} : get all the resumeSoftxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeSoftxes in body.
     */
    @GetMapping("/resume-softxes")
    public List<ResumeSoftx> getAllResumeSoftxes() {
        log.debug("REST request to get all ResumeSoftxes");
        return resumeSoftxRepository.findAll();
    }

    /**
     * {@code GET  /resume-softxes/:id} : get the "id" resumeSoftx.
     *
     * @param id the id of the resumeSoftx to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeSoftx, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-softxes/{id}")
    public ResponseEntity<ResumeSoftx> getResumeSoftx(@PathVariable Long id) {
        log.debug("REST request to get ResumeSoftx : {}", id);
        Optional<ResumeSoftx> resumeSoftx = resumeSoftxRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeSoftx);
    }

    /**
     * {@code DELETE  /resume-softxes/:id} : delete the "id" resumeSoftx.
     *
     * @param id the id of the resumeSoftx to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-softxes/{id}")
    public ResponseEntity<Void> deleteResumeSoftx(@PathVariable Long id) {
        log.debug("REST request to delete ResumeSoftx : {}", id);
        resumeSoftxRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
