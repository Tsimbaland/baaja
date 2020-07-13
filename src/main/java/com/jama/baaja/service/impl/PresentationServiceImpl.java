package com.jama.baaja.service.impl;

import com.jama.baaja.domain.Presentation;
import com.jama.baaja.repository.PresentationRepository;
import com.jama.baaja.service.PresentationService;
import com.jama.baaja.service.dto.PresentationDTO;
import com.jama.baaja.service.mapper.PresentationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Presentation}.
 */
@Service
@Transactional
public class PresentationServiceImpl implements PresentationService {

    private final Logger log = LoggerFactory.getLogger(PresentationServiceImpl.class);

    private final PresentationRepository presentationRepository;

    private final PresentationMapper presentationMapper;

    public PresentationServiceImpl(PresentationRepository presentationRepository, PresentationMapper presentationMapper) {
        this.presentationRepository = presentationRepository;
        this.presentationMapper = presentationMapper;
    }

    /**
     * Save a presentation.
     *
     * @param presentationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PresentationDTO save(PresentationDTO presentationDTO) {
        log.debug("Request to save Presentation : {}", presentationDTO);
        Presentation presentation = presentationMapper.toEntity(presentationDTO);
        presentation = presentationRepository.save(presentation);
        return presentationMapper.toDto(presentation);
    }

    /**
     * Get all the presentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PresentationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Presentations");
        return presentationRepository.findAll(pageable)
            .map(presentationMapper::toDto);
    }


    /**
     * Get one presentation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresentationDTO> findOne(Long id) {
        log.debug("Request to get Presentation : {}", id);
        return presentationRepository.findById(id)
            .map(presentationMapper::toDto);
    }

    /**
     * Delete the presentation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentation : {}", id);
        presentationRepository.deleteById(id);
    }
}
