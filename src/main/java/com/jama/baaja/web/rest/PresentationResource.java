package com.jama.baaja.web.rest;

import com.jama.baaja.service.PresentationService;
import com.jama.baaja.service.dto.PresentationDTO;
import com.jama.baaja.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jama.baaja.domain.Presentation}.
 */
@RestController
@RequestMapping("/api")
public class PresentationResource {

    private final Logger log = LoggerFactory.getLogger(PresentationResource.class);

    private static final String ENTITY_NAME = "presentation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresentationService presentationService;

    public PresentationResource(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    /**
     * {@code POST  /presentations} : Create a new presentation.
     *
     * @param presentationDTO the presentationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presentationDTO, or with status {@code 400 (Bad Request)} if the presentation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presentations")
    public ResponseEntity<PresentationDTO> createPresentation(@Valid @RequestBody PresentationDTO presentationDTO) throws URISyntaxException {
        log.debug("REST request to save Presentation : {}", presentationDTO);
        if (presentationDTO.getId() != null) {
            throw new BadRequestAlertException("A new presentation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresentationDTO result = presentationService.save(presentationDTO);
        return ResponseEntity.created(new URI("/api/presentations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presentations} : Updates an existing presentation.
     *
     * @param presentationDTO the presentationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentationDTO,
     * or with status {@code 400 (Bad Request)} if the presentationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presentationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presentations")
    public ResponseEntity<PresentationDTO> updatePresentation(@Valid @RequestBody PresentationDTO presentationDTO) throws URISyntaxException {
        log.debug("REST request to update Presentation : {}", presentationDTO);
        if (presentationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PresentationDTO result = presentationService.save(presentationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, presentationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /presentations} : get all the presentations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presentations in body.
     */
    @GetMapping("/presentations")
    public ResponseEntity<List<PresentationDTO>> getAllPresentations(Pageable pageable) {
        log.debug("REST request to get a page of Presentations");
        Page<PresentationDTO> page = presentationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presentations/:id} : get the "id" presentation.
     *
     * @param id the id of the presentationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presentationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presentations/{id}")
    public ResponseEntity<PresentationDTO> getPresentation(@PathVariable Long id) {
        log.debug("REST request to get Presentation : {}", id);
        Optional<PresentationDTO> presentationDTO = presentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presentationDTO);
    }

    /**
     * {@code DELETE  /presentations/:id} : delete the "id" presentation.
     *
     * @param id the id of the presentationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presentations/{id}")
    public ResponseEntity<Void> deletePresentation(@PathVariable Long id) {
        log.debug("REST request to delete Presentation : {}", id);
        presentationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
