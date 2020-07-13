package com.jama.baaja.web.rest;

import com.jama.baaja.service.SlideService;
import com.jama.baaja.service.dto.SlideDTO;
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
 * REST controller for managing {@link com.jama.baaja.domain.Slide}.
 */
@RestController
@RequestMapping("/api")
public class SlideResource {

    private final Logger log = LoggerFactory.getLogger(SlideResource.class);

    private static final String ENTITY_NAME = "slide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlideService slideService;

    public SlideResource(SlideService slideService) {
        this.slideService = slideService;
    }

    /**
     * {@code POST  /slides} : Create a new slide.
     *
     * @param slideDTO the slideDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slideDTO, or with status {@code 400 (Bad Request)} if the slide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slides")
    public ResponseEntity<SlideDTO> createSlide(@RequestBody SlideDTO slideDTO) throws URISyntaxException {
        log.debug("REST request to save Slide : {}", slideDTO);
        if (slideDTO.getId() != null) {
            throw new BadRequestAlertException("A new slide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.created(new URI("/api/slides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slides} : Updates an existing slide.
     *
     * @param slideDTO the slideDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slideDTO,
     * or with status {@code 400 (Bad Request)} if the slideDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slideDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slides")
    public ResponseEntity<SlideDTO> updateSlide(@RequestBody SlideDTO slideDTO) throws URISyntaxException {
        log.debug("REST request to update Slide : {}", slideDTO);
        if (slideDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, slideDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /slides} : get all the slides.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slides in body.
     */
    @GetMapping("/slides")
    public List<SlideDTO> getAllSlides() {
        log.debug("REST request to get all Slides");
        return slideService.findAll();
    }

    /**
     * {@code GET  /slides/:id} : get the "id" slide.
     *
     * @param id the id of the slideDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slideDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slides/{id}")
    public ResponseEntity<SlideDTO> getSlide(@PathVariable Long id) {
        log.debug("REST request to get Slide : {}", id);
        Optional<SlideDTO> slideDTO = slideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slideDTO);
    }

    /**
     * {@code DELETE  /slides/:id} : delete the "id" slide.
     *
     * @param id the id of the slideDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slides/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id) {
        log.debug("REST request to delete Slide : {}", id);
        slideService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
