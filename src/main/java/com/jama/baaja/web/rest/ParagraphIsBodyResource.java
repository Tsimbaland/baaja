package com.jama.baaja.web.rest;

import com.jama.baaja.domain.ParagraphIsBody;
import com.jama.baaja.repository.ParagraphIsBodyRepository;
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
 * REST controller for managing {@link com.jama.baaja.domain.ParagraphIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParagraphIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(ParagraphIsBodyResource.class);

    private static final String ENTITY_NAME = "paragraphIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParagraphIsBodyRepository paragraphIsBodyRepository;

    public ParagraphIsBodyResource(ParagraphIsBodyRepository paragraphIsBodyRepository) {
        this.paragraphIsBodyRepository = paragraphIsBodyRepository;
    }

    /**
     * {@code POST  /paragraph-is-bodies} : Create a new paragraphIsBody.
     *
     * @param paragraphIsBody the paragraphIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paragraphIsBody, or with status {@code 400 (Bad Request)} if the paragraphIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paragraph-is-bodies")
    public ResponseEntity<ParagraphIsBody> createParagraphIsBody(@RequestBody ParagraphIsBody paragraphIsBody) throws URISyntaxException {
        log.debug("REST request to save ParagraphIsBody : {}", paragraphIsBody);
        if (paragraphIsBody.getId() != null) {
            throw new BadRequestAlertException("A new paragraphIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParagraphIsBody result = paragraphIsBodyRepository.save(paragraphIsBody);
        return ResponseEntity.created(new URI("/api/paragraph-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paragraph-is-bodies} : Updates an existing paragraphIsBody.
     *
     * @param paragraphIsBody the paragraphIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paragraphIsBody,
     * or with status {@code 400 (Bad Request)} if the paragraphIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paragraphIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paragraph-is-bodies")
    public ResponseEntity<ParagraphIsBody> updateParagraphIsBody(@RequestBody ParagraphIsBody paragraphIsBody) throws URISyntaxException {
        log.debug("REST request to update ParagraphIsBody : {}", paragraphIsBody);
        if (paragraphIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParagraphIsBody result = paragraphIsBodyRepository.save(paragraphIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paragraphIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paragraph-is-bodies} : get all the paragraphIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paragraphIsBodies in body.
     */
    @GetMapping("/paragraph-is-bodies")
    public List<ParagraphIsBody> getAllParagraphIsBodies() {
        log.debug("REST request to get all ParagraphIsBodies");
        return paragraphIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /paragraph-is-bodies/:id} : get the "id" paragraphIsBody.
     *
     * @param id the id of the paragraphIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paragraphIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paragraph-is-bodies/{id}")
    public ResponseEntity<ParagraphIsBody> getParagraphIsBody(@PathVariable Long id) {
        log.debug("REST request to get ParagraphIsBody : {}", id);
        Optional<ParagraphIsBody> paragraphIsBody = paragraphIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paragraphIsBody);
    }

    /**
     * {@code DELETE  /paragraph-is-bodies/:id} : delete the "id" paragraphIsBody.
     *
     * @param id the id of the paragraphIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paragraph-is-bodies/{id}")
    public ResponseEntity<Void> deleteParagraphIsBody(@PathVariable Long id) {
        log.debug("REST request to delete ParagraphIsBody : {}", id);
        paragraphIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
