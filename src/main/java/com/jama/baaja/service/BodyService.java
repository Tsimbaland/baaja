package com.jama.baaja.service;

import com.jama.baaja.service.dto.BodyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jama.baaja.domain.Body}.
 */
public interface BodyService {

    /**
     * Save a body.
     *
     * @param bodyDTO the entity to save.
     * @return the persisted entity.
     */
    BodyDTO save(BodyDTO bodyDTO);

    /**
     * Get all the bodies.
     *
     * @return the list of entities.
     */
    List<BodyDTO> findAll();


    /**
     * Get the "id" body.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BodyDTO> findOne(Long id);

    /**
     * Delete the "id" body.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
