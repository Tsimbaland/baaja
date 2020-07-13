package com.jama.baaja.service.impl;

import com.jama.baaja.domain.Body;
import com.jama.baaja.repository.BodyRepository;
import com.jama.baaja.service.BodyService;
import com.jama.baaja.service.dto.BodyDTO;
import com.jama.baaja.service.mapper.BodyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Body}.
 */
@Service
@Transactional
public class BodyServiceImpl implements BodyService {

    private final Logger log = LoggerFactory.getLogger(BodyServiceImpl.class);

    private final BodyRepository bodyRepository;

    private final BodyMapper bodyMapper;

    public BodyServiceImpl(BodyRepository bodyRepository, BodyMapper bodyMapper) {
        this.bodyRepository = bodyRepository;
        this.bodyMapper = bodyMapper;
    }

    /**
     * Save a body.
     *
     * @param bodyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BodyDTO save(BodyDTO bodyDTO) {
        log.debug("Request to save Body : {}", bodyDTO);
        Body body = bodyMapper.toEntity(bodyDTO);
        body = bodyRepository.save(body);
        return bodyMapper.toDto(body);
    }

    /**
     * Get all the bodies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BodyDTO> findAll() {
        log.debug("Request to get all Bodies");
        return bodyRepository.findAll().stream()
            .map(bodyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one body by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BodyDTO> findOne(Long id) {
        log.debug("Request to get Body : {}", id);
        return bodyRepository.findById(id)
            .map(bodyMapper::toDto);
    }

    /**
     * Delete the body by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Body : {}", id);
        bodyRepository.deleteById(id);
    }
}
