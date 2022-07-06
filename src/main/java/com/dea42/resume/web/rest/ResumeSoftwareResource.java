package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeSoftware;
import com.dea42.resume.repository.ResumeSoftwareRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeSoftware}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeSoftwareResource {

    private final Logger log = LoggerFactory.getLogger(ResumeSoftwareResource.class);

    private static final String ENTITY_NAME = "resumeSoftware";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeSoftwareRepository resumeSoftwareRepository;

    public ResumeSoftwareResource(ResumeSoftwareRepository resumeSoftwareRepository) {
        this.resumeSoftwareRepository = resumeSoftwareRepository;
    }

    /**
     * {@code POST  /resume-softwares} : Create a new resumeSoftware.
     *
     * @param resumeSoftware the resumeSoftware to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeSoftware, or with status {@code 400 (Bad Request)} if the resumeSoftware has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-softwares")
    public ResponseEntity<ResumeSoftware> createResumeSoftware(@Valid @RequestBody ResumeSoftware resumeSoftware)
        throws URISyntaxException {
        log.debug("REST request to save ResumeSoftware : {}", resumeSoftware);
        if (resumeSoftware.getId() != null) {
            throw new BadRequestAlertException("A new resumeSoftware cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeSoftware result = resumeSoftwareRepository.save(resumeSoftware);
        return ResponseEntity
            .created(new URI("/api/resume-softwares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-softwares/:id} : Updates an existing resumeSoftware.
     *
     * @param id the id of the resumeSoftware to save.
     * @param resumeSoftware the resumeSoftware to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSoftware,
     * or with status {@code 400 (Bad Request)} if the resumeSoftware is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeSoftware couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-softwares/{id}")
    public ResponseEntity<ResumeSoftware> updateResumeSoftware(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumeSoftware resumeSoftware
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeSoftware : {}, {}", id, resumeSoftware);
        if (resumeSoftware.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSoftware.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSoftwareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeSoftware result = resumeSoftwareRepository.save(resumeSoftware);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSoftware.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-softwares/:id} : Partial updates given fields of an existing resumeSoftware, field will ignore if it is null
     *
     * @param id the id of the resumeSoftware to save.
     * @param resumeSoftware the resumeSoftware to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeSoftware,
     * or with status {@code 400 (Bad Request)} if the resumeSoftware is not valid,
     * or with status {@code 404 (Not Found)} if the resumeSoftware is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeSoftware couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-softwares/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeSoftware> partialUpdateResumeSoftware(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumeSoftware resumeSoftware
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeSoftware partially : {}, {}", id, resumeSoftware);
        if (resumeSoftware.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeSoftware.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeSoftwareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeSoftware> result = resumeSoftwareRepository
            .findById(resumeSoftware.getId())
            .map(existingResumeSoftware -> {
                if (resumeSoftware.getSoftwareName() != null) {
                    existingResumeSoftware.setSoftwareName(resumeSoftware.getSoftwareName());
                }
                if (resumeSoftware.getSoftwareVer() != null) {
                    existingResumeSoftware.setSoftwareVer(resumeSoftware.getSoftwareVer());
                }
                if (resumeSoftware.getSoftwareCatId() != null) {
                    existingResumeSoftware.setSoftwareCatId(resumeSoftware.getSoftwareCatId());
                }
                if (resumeSoftware.getSummarryCatId() != null) {
                    existingResumeSoftware.setSummarryCatId(resumeSoftware.getSummarryCatId());
                }

                return existingResumeSoftware;
            })
            .map(resumeSoftwareRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeSoftware.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-softwares} : get all the resumeSoftwares.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeSoftwares in body.
     */
    @GetMapping("/resume-softwares")
    public List<ResumeSoftware> getAllResumeSoftwares() {
        log.debug("REST request to get all ResumeSoftwares");
        return resumeSoftwareRepository.findAll();
    }

    /**
     * {@code GET  /resume-softwares/:id} : get the "id" resumeSoftware.
     *
     * @param id the id of the resumeSoftware to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeSoftware, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-softwares/{id}")
    public ResponseEntity<ResumeSoftware> getResumeSoftware(@PathVariable Long id) {
        log.debug("REST request to get ResumeSoftware : {}", id);
        Optional<ResumeSoftware> resumeSoftware = resumeSoftwareRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeSoftware);
    }

    /**
     * {@code DELETE  /resume-softwares/:id} : delete the "id" resumeSoftware.
     *
     * @param id the id of the resumeSoftware to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-softwares/{id}")
    public ResponseEntity<Void> deleteResumeSoftware(@PathVariable Long id) {
        log.debug("REST request to delete ResumeSoftware : {}", id);
        resumeSoftwareRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
