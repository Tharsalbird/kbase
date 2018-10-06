package kbase.service.impl;

import kbase.service.RotuloService;
import kbase.domain.Rotulo;
import kbase.repository.RotuloRepository;
import kbase.service.dto.RotuloDTO;
import kbase.service.mapper.RotuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Rotulo.
 */
@Service
@Transactional
public class RotuloServiceImpl implements RotuloService {

    private final Logger log = LoggerFactory.getLogger(RotuloServiceImpl.class);

    private final RotuloRepository rotuloRepository;

    private final RotuloMapper rotuloMapper;

    public RotuloServiceImpl(RotuloRepository rotuloRepository, RotuloMapper rotuloMapper) {
        this.rotuloRepository = rotuloRepository;
        this.rotuloMapper = rotuloMapper;
    }

    /**
     * Save a rotulo.
     *
     * @param rotuloDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RotuloDTO save(RotuloDTO rotuloDTO) {
        log.debug("Request to save Rotulo : {}", rotuloDTO);
        Rotulo rotulo = rotuloMapper.toEntity(rotuloDTO);
        rotulo = rotuloRepository.save(rotulo);
        return rotuloMapper.toDto(rotulo);
    }

    /**
     * Get all the rotulos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RotuloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rotulos");
        return rotuloRepository.findAll(pageable)
            .map(rotuloMapper::toDto);
    }


    /**
     * Get one rotulo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RotuloDTO> findOne(Long id) {
        log.debug("Request to get Rotulo : {}", id);
        return rotuloRepository.findById(id)
            .map(rotuloMapper::toDto);
    }

    /**
     * Delete the rotulo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rotulo : {}", id);
        rotuloRepository.deleteById(id);
    }
}
