package com.jama.baaja.web.rest;

import com.jama.baaja.service.BodyService;
import com.jama.baaja.service.dto.BodyDTO;
import com.jama.baaja.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
 * REST controller for managing {@link com.jama.baaja.domain.Body}.
 */
@RestController
@RequestMapping("/api")
public class BodyResource {

    private final Logger log = LoggerFactory.getLogger(BodyResource.class);

    private static final String ENTITY_NAME = "body";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyService bodyService;

    public BodyResource(BodyService bodyService) {
        this.bodyService = bodyService;
    }

    /**
     * {@code POST  /bodies} : Create a new body.
     *
     * @param bodyDTO the bodyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyDTO, or with status {@code 400 (Bad Request)} if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bodies")
    public ResponseEntity<BodyDTO> createBody(@RequestBody BodyDTO bodyDTO) throws URISyntaxException {
        log.debug("REST request to save Body : {}", bodyDTO);
        if (bodyDTO.getId() != null) {
            throw new BadRequestAlertException("A new body cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyDTO result = bodyService.save(bodyDTO);
        return ResponseEntity.created(new URI("/api/bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bodies} : Updates an existing body.
     *
     * @param bodyDTO the bodyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyDTO,
     * or with status {@code 400 (Bad Request)} if the bodyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bodies")
    public ResponseEntity<BodyDTO> updateBody(@RequestBody BodyDTO bodyDTO) throws URISyntaxException {
        log.debug("REST request to update Body : {}", bodyDTO);
        if (bodyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BodyDTO result = bodyService.save(bodyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bodyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bodies} : get all the bodies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodies in body.
     */
    @GetMapping("/bodies")
    public List<BodyDTO> getAllBodies() {
        log.debug("REST request to get all Bodies");
        return bodyService.findAll();
    }

    /**
     * {@code GET  /bodies/:id} : get the "id" body.
     *
     * @param id the id of the bodyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bodies/{id}")
    public ResponseEntity<BodyDTO> getBody(@PathVariable Long id) {
        log.debug("REST request to get Body : {}", id);
        Optional<BodyDTO> bodyDTO = bodyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bodyDTO);
    }

    /**
     * {@code DELETE  /bodies/:id} : delete the "id" body.
     *
     * @param id the id of the bodyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bodies/{id}")
    public ResponseEntity<Void> deleteBody(@PathVariable Long id) {
        log.debug("REST request to delete Body : {}", id);
        bodyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
