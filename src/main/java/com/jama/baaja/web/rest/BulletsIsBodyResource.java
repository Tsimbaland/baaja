package com.jama.baaja.web.rest;

import com.jama.baaja.domain.BulletsIsBody;
import com.jama.baaja.repository.BulletsIsBodyRepository;
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
 * REST controller for managing {@link com.jama.baaja.domain.BulletsIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BulletsIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(BulletsIsBodyResource.class);

    private static final String ENTITY_NAME = "bulletsIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BulletsIsBodyRepository bulletsIsBodyRepository;

    public BulletsIsBodyResource(BulletsIsBodyRepository bulletsIsBodyRepository) {
        this.bulletsIsBodyRepository = bulletsIsBodyRepository;
    }

    /**
     * {@code POST  /bullets-is-bodies} : Create a new bulletsIsBody.
     *
     * @param bulletsIsBody the bulletsIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bulletsIsBody, or with status {@code 400 (Bad Request)} if the bulletsIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bullets-is-bodies")
    public ResponseEntity<BulletsIsBody> createBulletsIsBody(@RequestBody BulletsIsBody bulletsIsBody) throws URISyntaxException {
        log.debug("REST request to save BulletsIsBody : {}", bulletsIsBody);
        if (bulletsIsBody.getId() != null) {
            throw new BadRequestAlertException("A new bulletsIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BulletsIsBody result = bulletsIsBodyRepository.save(bulletsIsBody);
        return ResponseEntity.created(new URI("/api/bullets-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bullets-is-bodies} : Updates an existing bulletsIsBody.
     *
     * @param bulletsIsBody the bulletsIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bulletsIsBody,
     * or with status {@code 400 (Bad Request)} if the bulletsIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bulletsIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bullets-is-bodies")
    public ResponseEntity<BulletsIsBody> updateBulletsIsBody(@RequestBody BulletsIsBody bulletsIsBody) throws URISyntaxException {
        log.debug("REST request to update BulletsIsBody : {}", bulletsIsBody);
        if (bulletsIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BulletsIsBody result = bulletsIsBodyRepository.save(bulletsIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bulletsIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bullets-is-bodies} : get all the bulletsIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bulletsIsBodies in body.
     */
    @GetMapping("/bullets-is-bodies")
    public List<BulletsIsBody> getAllBulletsIsBodies() {
        log.debug("REST request to get all BulletsIsBodies");
        return bulletsIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /bullets-is-bodies/:id} : get the "id" bulletsIsBody.
     *
     * @param id the id of the bulletsIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bulletsIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bullets-is-bodies/{id}")
    public ResponseEntity<BulletsIsBody> getBulletsIsBody(@PathVariable Long id) {
        log.debug("REST request to get BulletsIsBody : {}", id);
        Optional<BulletsIsBody> bulletsIsBody = bulletsIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bulletsIsBody);
    }

    /**
     * {@code DELETE  /bullets-is-bodies/:id} : delete the "id" bulletsIsBody.
     *
     * @param id the id of the bulletsIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bullets-is-bodies/{id}")
    public ResponseEntity<Void> deleteBulletsIsBody(@PathVariable Long id) {
        log.debug("REST request to delete BulletsIsBody : {}", id);
        bulletsIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
