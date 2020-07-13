package com.jama.baaja.web.rest;

import com.jama.baaja.domain.TypeIsBody;
import com.jama.baaja.repository.TypeIsBodyRepository;
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
 * REST controller for managing {@link com.jama.baaja.domain.TypeIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(TypeIsBodyResource.class);

    private static final String ENTITY_NAME = "typeIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeIsBodyRepository typeIsBodyRepository;

    public TypeIsBodyResource(TypeIsBodyRepository typeIsBodyRepository) {
        this.typeIsBodyRepository = typeIsBodyRepository;
    }

    /**
     * {@code POST  /type-is-bodies} : Create a new typeIsBody.
     *
     * @param typeIsBody the typeIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeIsBody, or with status {@code 400 (Bad Request)} if the typeIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-is-bodies")
    public ResponseEntity<TypeIsBody> createTypeIsBody(@RequestBody TypeIsBody typeIsBody) throws URISyntaxException {
        log.debug("REST request to save TypeIsBody : {}", typeIsBody);
        if (typeIsBody.getId() != null) {
            throw new BadRequestAlertException("A new typeIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeIsBody result = typeIsBodyRepository.save(typeIsBody);
        return ResponseEntity.created(new URI("/api/type-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-is-bodies} : Updates an existing typeIsBody.
     *
     * @param typeIsBody the typeIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeIsBody,
     * or with status {@code 400 (Bad Request)} if the typeIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-is-bodies")
    public ResponseEntity<TypeIsBody> updateTypeIsBody(@RequestBody TypeIsBody typeIsBody) throws URISyntaxException {
        log.debug("REST request to update TypeIsBody : {}", typeIsBody);
        if (typeIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeIsBody result = typeIsBodyRepository.save(typeIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-is-bodies} : get all the typeIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeIsBodies in body.
     */
    @GetMapping("/type-is-bodies")
    public List<TypeIsBody> getAllTypeIsBodies() {
        log.debug("REST request to get all TypeIsBodies");
        return typeIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /type-is-bodies/:id} : get the "id" typeIsBody.
     *
     * @param id the id of the typeIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-is-bodies/{id}")
    public ResponseEntity<TypeIsBody> getTypeIsBody(@PathVariable Long id) {
        log.debug("REST request to get TypeIsBody : {}", id);
        Optional<TypeIsBody> typeIsBody = typeIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeIsBody);
    }

    /**
     * {@code DELETE  /type-is-bodies/:id} : delete the "id" typeIsBody.
     *
     * @param id the id of the typeIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-is-bodies/{id}")
    public ResponseEntity<Void> deleteTypeIsBody(@PathVariable Long id) {
        log.debug("REST request to delete TypeIsBody : {}", id);
        typeIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
