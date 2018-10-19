package kbase.service;

import kbase.domain.ChaveErro;
import kbase.repository.ChaveErroRepository;
import kbase.service.dto.ChaveErroDTO;
import kbase.service.mapper.ChaveErroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ChaveErro.
 */
@Service
@Transactional
public class ChaveErroService {

    private final Logger log = LoggerFactory.getLogger(ChaveErroService.class);

    private final ChaveErroRepository chaveErroRepository;

    private final ChaveErroMapper chaveErroMapper;

    public ChaveErroService(ChaveErroRepository chaveErroRepository, ChaveErroMapper chaveErroMapper) {
        this.chaveErroRepository = chaveErroRepository;
        this.chaveErroMapper = chaveErroMapper;
    }

    /**
     * Save a chaveErro.
     *
     * @param chaveErroDTO the entity to save
     * @return the persisted entity
     */
    public ChaveErroDTO save(ChaveErroDTO chaveErroDTO) {
        log.debug("Request to save ChaveErro : {}", chaveErroDTO);
        ChaveErro chaveErro = chaveErroMapper.toEntity(chaveErroDTO);
        chaveErro = chaveErroRepository.save(chaveErro);
        return chaveErroMapper.toDto(chaveErro);
    }

    /**
     * Get all the chaveErros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChaveErroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChaveErros");
        return chaveErroRepository.findAll(pageable)
            .map(chaveErroMapper::toDto);
    }


    /**
     * Get one chaveErro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChaveErroDTO> findOne(Long id) {
        log.debug("Request to get ChaveErro : {}", id);
        return chaveErroRepository.findById(id)
            .map(chaveErroMapper::toDto);
    }

    /**
     * Delete the chaveErro by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChaveErro : {}", id);
        chaveErroRepository.deleteById(id);
    }
}
