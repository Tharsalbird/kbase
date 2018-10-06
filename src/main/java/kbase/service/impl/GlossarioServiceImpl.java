package kbase.service.impl;

import kbase.service.GlossarioService;
import kbase.domain.Glossario;
import kbase.repository.GlossarioRepository;
import kbase.service.dto.GlossarioDTO;
import kbase.service.mapper.GlossarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Glossario.
 */
@Service
@Transactional
public class GlossarioServiceImpl implements GlossarioService {

    private final Logger log = LoggerFactory.getLogger(GlossarioServiceImpl.class);

    private final GlossarioRepository glossarioRepository;

    private final GlossarioMapper glossarioMapper;

    public GlossarioServiceImpl(GlossarioRepository glossarioRepository, GlossarioMapper glossarioMapper) {
        this.glossarioRepository = glossarioRepository;
        this.glossarioMapper = glossarioMapper;
    }

    /**
     * Save a glossario.
     *
     * @param glossarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GlossarioDTO save(GlossarioDTO glossarioDTO) {
        log.debug("Request to save Glossario : {}", glossarioDTO);
        Glossario glossario = glossarioMapper.toEntity(glossarioDTO);
        glossario = glossarioRepository.save(glossario);
        return glossarioMapper.toDto(glossario);
    }

    /**
     * Get all the glossarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GlossarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Glossarios");
        return glossarioRepository.findAll(pageable)
            .map(glossarioMapper::toDto);
    }


    /**
     * Get one glossario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GlossarioDTO> findOne(Long id) {
        log.debug("Request to get Glossario : {}", id);
        return glossarioRepository.findById(id)
            .map(glossarioMapper::toDto);
    }

    /**
     * Delete the glossario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Glossario : {}", id);
        glossarioRepository.deleteById(id);
    }
}
