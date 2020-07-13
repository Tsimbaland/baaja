package com.jama.baaja.web.rest;

import com.jama.baaja.domain.SelectIsBody;
import com.jama.baaja.repository.SelectIsBodyRepository;
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
 * REST controller for managing {@link com.jama.baaja.domain.SelectIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SelectIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(SelectIsBodyResource.class);

    private static final String ENTITY_NAME = "selectIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SelectIsBodyRepository selectIsBodyRepository;

    public SelectIsBodyResource(SelectIsBodyRepository selectIsBodyRepository) {
        this.selectIsBodyRepository = selectIsBodyRepository;
    }

    /**
     * {@code POST  /select-is-bodies} : Create a new selectIsBody.
     *
     * @param selectIsBody the selectIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selectIsBody, or with status {@code 400 (Bad Request)} if the selectIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/select-is-bodies")
    public ResponseEntity<SelectIsBody> createSelectIsBody(@RequestBody SelectIsBody selectIsBody) throws URISyntaxException {
        log.debug("REST request to save SelectIsBody : {}", selectIsBody);
        if (selectIsBody.getId() != null) {
            throw new BadRequestAlertException("A new selectIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelectIsBody result = selectIsBodyRepository.save(selectIsBody);
        return ResponseEntity.created(new URI("/api/select-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /select-is-bodies} : Updates an existing selectIsBody.
     *
     * @param selectIsBody the selectIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selectIsBody,
     * or with status {@code 400 (Bad Request)} if the selectIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selectIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/select-is-bodies")
    public ResponseEntity<SelectIsBody> updateSelectIsBody(@RequestBody SelectIsBody selectIsBody) throws URISyntaxException {
        log.debug("REST request to update SelectIsBody : {}", selectIsBody);
        if (selectIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SelectIsBody result = selectIsBodyRepository.save(selectIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, selectIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /select-is-bodies} : get all the selectIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectIsBodies in body.
     */
    @GetMapping("/select-is-bodies")
    public List<SelectIsBody> getAllSelectIsBodies() {
        log.debug("REST request to get all SelectIsBodies");
        return selectIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /select-is-bodies/:id} : get the "id" selectIsBody.
     *
     * @param id the id of the selectIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selectIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/select-is-bodies/{id}")
    public ResponseEntity<SelectIsBody> getSelectIsBody(@PathVariable Long id) {
        log.debug("REST request to get SelectIsBody : {}", id);
        Optional<SelectIsBody> selectIsBody = selectIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(selectIsBody);
    }

    /**
     * {@code DELETE  /select-is-bodies/:id} : delete the "id" selectIsBody.
     *
     * @param id the id of the selectIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/select-is-bodies/{id}")
    public ResponseEntity<Void> deleteSelectIsBody(@PathVariable Long id) {
        log.debug("REST request to delete SelectIsBody : {}", id);
        selectIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
