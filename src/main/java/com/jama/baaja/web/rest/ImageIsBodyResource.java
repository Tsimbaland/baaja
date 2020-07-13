package com.jama.baaja.web.rest;

import com.jama.baaja.domain.ImageIsBody;
import com.jama.baaja.repository.ImageIsBodyRepository;
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
 * REST controller for managing {@link com.jama.baaja.domain.ImageIsBody}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ImageIsBodyResource {

    private final Logger log = LoggerFactory.getLogger(ImageIsBodyResource.class);

    private static final String ENTITY_NAME = "imageIsBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageIsBodyRepository imageIsBodyRepository;

    public ImageIsBodyResource(ImageIsBodyRepository imageIsBodyRepository) {
        this.imageIsBodyRepository = imageIsBodyRepository;
    }

    /**
     * {@code POST  /image-is-bodies} : Create a new imageIsBody.
     *
     * @param imageIsBody the imageIsBody to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageIsBody, or with status {@code 400 (Bad Request)} if the imageIsBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-is-bodies")
    public ResponseEntity<ImageIsBody> createImageIsBody(@RequestBody ImageIsBody imageIsBody) throws URISyntaxException {
        log.debug("REST request to save ImageIsBody : {}", imageIsBody);
        if (imageIsBody.getId() != null) {
            throw new BadRequestAlertException("A new imageIsBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageIsBody result = imageIsBodyRepository.save(imageIsBody);
        return ResponseEntity.created(new URI("/api/image-is-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-is-bodies} : Updates an existing imageIsBody.
     *
     * @param imageIsBody the imageIsBody to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageIsBody,
     * or with status {@code 400 (Bad Request)} if the imageIsBody is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageIsBody couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-is-bodies")
    public ResponseEntity<ImageIsBody> updateImageIsBody(@RequestBody ImageIsBody imageIsBody) throws URISyntaxException {
        log.debug("REST request to update ImageIsBody : {}", imageIsBody);
        if (imageIsBody.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageIsBody result = imageIsBodyRepository.save(imageIsBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, imageIsBody.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /image-is-bodies} : get all the imageIsBodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageIsBodies in body.
     */
    @GetMapping("/image-is-bodies")
    public List<ImageIsBody> getAllImageIsBodies() {
        log.debug("REST request to get all ImageIsBodies");
        return imageIsBodyRepository.findAll();
    }

    /**
     * {@code GET  /image-is-bodies/:id} : get the "id" imageIsBody.
     *
     * @param id the id of the imageIsBody to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageIsBody, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-is-bodies/{id}")
    public ResponseEntity<ImageIsBody> getImageIsBody(@PathVariable Long id) {
        log.debug("REST request to get ImageIsBody : {}", id);
        Optional<ImageIsBody> imageIsBody = imageIsBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(imageIsBody);
    }

    /**
     * {@code DELETE  /image-is-bodies/:id} : delete the "id" imageIsBody.
     *
     * @param id the id of the imageIsBody to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-is-bodies/{id}")
    public ResponseEntity<Void> deleteImageIsBody(@PathVariable Long id) {
        log.debug("REST request to delete ImageIsBody : {}", id);
        imageIsBodyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
