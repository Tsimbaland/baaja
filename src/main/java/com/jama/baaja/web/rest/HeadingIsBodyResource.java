package com.jama.baaja.web.rest;

import com.jama.baaja.domain.HeadingIsBody;
import com.jama.baaja.repository.HeadingIsBodyRepository;
import com.jama.baaja.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jama.baaja.domain.HeadingIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HeadingIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(HeadingIsBodyResource.class);

    private static final String ENTITY_NAME = "headingIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeadingIsBodyRepository headingIsBodyRepository;

    public HeadingIsBodyResource(HeadingIsBodyRepository headingIsBodyRepository) {
        this.headingIsBodyRepository = headingIsBodyRepository;
    }

    /**
     * {@code POST  /heading-is-bodies} : Create a new headingIsBody.
     *
     * @param headingIsBody the headingIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new headingIsBody, or with status {@code 400 (Bad Request)} if the headingIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heading-is-bodies")
    public ResponseEntity<HeadingIsBody> createHeadingIsBody(@RequestBody HeadingIsBody headingIsBody) throws URISyntaxException {
        log.debug("REST request to save HeadingIsBody : {}", headingIsBody);
        if (headingIsBody.getId() != null) {
            throw new BadRequestAlertException("A new headingIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeadingIsBody result = headingIsBodyRepository.save(headingIsBody);
        return ResponseEntity.created(new URI("/api/heading-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heading-is-bodies} : Updates an existing headingIsBody.
     *
     * @param headingIsBody the headingIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated headingIsBody,
     * or with status {@code 400 (Bad Request)} if the headingIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the headingIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heading-is-bodies")
    public ResponseEntity<HeadingIsBody> updateHeadingIsBody(@RequestBody HeadingIsBody headingIsBody) throws URISyntaxException {
        log.debug("REST request to update HeadingIsBody : {}", headingIsBody);
        if (headingIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HeadingIsBody result = headingIsBodyRepository.save(headingIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, headingIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /heading-is-bodies} : get all the headingIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of headingIsBodies in body.
     */
    @GetMapping("/heading-is-bodies")
    public List<HeadingIsBody> getAllHeadingIsBodies() {
        log.debug("REST request to get all HeadingIsBodies");
        return headingIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /heading-is-bodies/:id} : get the "id" headingIsBody.
     *
     * @param id the id of the headingIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the headingIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heading-is-bodies/{id}")
    public ResponseEntity<HeadingIsBody> getHeadingIsBody(@PathVariable Long id) {
        log.debug("REST request to get HeadingIsBody : {}", id);
        Optional<HeadingIsBody> headingIsBody = headingIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(headingIsBody);
    }

    /**
     * {@code DELETE  /heading-is-bodies/:id} : delete the "id" headingIsBody.
     *
     * @param id the id of the headingIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heading-is-bodies/{id}")
    public ResponseEntity<Void> deleteHeadingIsBody(@PathVariable Long id) {
        log.debug("REST request to delete HeadingIsBody : {}", id);
        headingIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
