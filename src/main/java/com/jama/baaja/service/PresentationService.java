package com.jama.baaja.service;

import com.jama.baaja.service.dto.PresentationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jama.baaja.domain.Presentation}.
 */
public interface PresentationService {

    /**
     * Save a presentation.
     *
     * @param presentationDTO the entity to save.
     * @return the persisted entity.
     */
    PresentationDTO save(PresentationDTO presentationDTO);

    /**
     * Get all the presentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PresentationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" presentation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PresentationDTO> findOne(Long id);

    /**
     * Delete the "id" presentation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
