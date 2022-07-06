package com.dea42.resume.web.rest;

import com.dea42.resume.domain.ResumeRespon;
import com.dea42.resume.repository.ResumeResponRepository;
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
 * REST controller for managing {@link com.dea42.resume.domain.ResumeRespon}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeResponResource {

    private final Logger log = LoggerFactory.getLogger(ResumeResponResource.class);

    private static final String ENTITY_NAME = "resumeRespon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeResponRepository resumeResponRepository;

    public ResumeResponResource(ResumeResponRepository resumeResponRepository) {
        this.resumeResponRepository = resumeResponRepository;
    }

    /**
     * {@code POST  /resume-respons} : Create a new resumeRespon.
     *
     * @param resumeRespon the resumeRespon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeRespon, or with status {@code 400 (Bad Request)} if the resumeRespon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-respons")
    public ResponseEntity<ResumeRespon> createResumeRespon(@Valid @RequestBody ResumeRespon resumeRespon) throws URISyntaxException {
        log.debug("REST request to save ResumeRespon : {}", resumeRespon);
        if (resumeRespon.getId() != null) {
            throw new BadRequestAlertException("A new resumeRespon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeRespon result = resumeResponRepository.save(resumeRespon);
        return ResponseEntity
            .created(new URI("/api/resume-respons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-respons/:id} : Updates an existing resumeRespon.
     *
     * @param id the id of the resumeRespon to save.
     * @param resumeRespon the resumeRespon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeRespon,
     * or with status {@code 400 (Bad Request)} if the resumeRespon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeRespon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-respons/{id}")
    public ResponseEntity<ResumeRespon> updateResumeRespon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumeRespon resumeRespon
    ) throws URISyntaxException {
        log.debug("REST request to update ResumeRespon : {}, {}", id, resumeRespon);
        if (resumeRespon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeRespon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeResponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumeRespon result = resumeResponRepository.save(resumeRespon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeRespon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resume-respons/:id} : Partial updates given fields of an existing resumeRespon, field will ignore if it is null
     *
     * @param id the id of the resumeRespon to save.
     * @param resumeRespon the resumeRespon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeRespon,
     * or with status {@code 400 (Bad Request)} if the resumeRespon is not valid,
     * or with status {@code 404 (Not Found)} if the resumeRespon is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumeRespon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resume-respons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumeRespon> partialUpdateResumeRespon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumeRespon resumeRespon
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumeRespon partially : {}, {}", id, resumeRespon);
        if (resumeRespon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumeRespon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeResponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumeRespon> result = resumeResponRepository
            .findById(resumeRespon.getId())
            .map(existingResumeRespon -> {
                if (resumeRespon.getJobId() != null) {
                    existingResumeRespon.setJobId(resumeRespon.getJobId());
                }
                if (resumeRespon.getResponOrder() != null) {
                    existingResumeRespon.setResponOrder(resumeRespon.getResponOrder());
                }
                if (resumeRespon.getResponShow() != null) {
                    existingResumeRespon.setResponShow(resumeRespon.getResponShow());
                }

                return existingResumeRespon;
            })
            .map(resumeResponRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumeRespon.getId().toString())
        );
    }

    /**
     * {@code GET  /resume-respons} : get all the resumeRespons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeRespons in body.
     */
    @GetMapping("/resume-respons")
    public List<ResumeRespon> getAllResumeRespons() {
        log.debug("REST request to get all ResumeRespons");
        return resumeResponRepository.findAll();
    }

    /**
     * {@code GET  /resume-respons/:id} : get the "id" resumeRespon.
     *
     * @param id the id of the resumeRespon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeRespon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-respons/{id}")
    public ResponseEntity<ResumeRespon> getResumeRespon(@PathVariable Long id) {
        log.debug("REST request to get ResumeRespon : {}", id);
        Optional<ResumeRespon> resumeRespon = resumeResponRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resumeRespon);
    }

    /**
     * {@code DELETE  /resume-respons/:id} : delete the "id" resumeRespon.
     *
     * @param id the id of the resumeRespon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-respons/{id}")
    public ResponseEntity<Void> deleteResumeRespon(@PathVariable Long id) {
        log.debug("REST request to delete ResumeRespon : {}", id);
        resumeResponRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
