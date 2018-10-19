package kbase.service;

import kbase.domain.Registro;
import kbase.repository.RegistroRepository;
import kbase.service.dto.RegistroDTO;
import kbase.service.mapper.RegistroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Registro.
 */
@Service
@Transactional
public class RegistroService {

    private final Logger log = LoggerFactory.getLogger(RegistroService.class);

    private final RegistroRepository registroRepository;

    private final RegistroMapper registroMapper;

    public RegistroService(RegistroRepository registroRepository, RegistroMapper registroMapper) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
    }

    /**
     * Save a registro.
     *
     * @param registroDTO the entity to save
     * @return the persisted entity
     */
    public RegistroDTO save(RegistroDTO registroDTO) {
        log.debug("Request to save Registro : {}", registroDTO);
        Registro registro = registroMapper.toEntity(registroDTO);
        registro = registroRepository.save(registro);
        return registroMapper.toDto(registro);
    }

    /**
     * Get all the registros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RegistroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Registros");
        return registroRepository.findAll(pageable)
            .map(registroMapper::toDto);
    }

    /**
     * Get all the Registro with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RegistroDTO> findAllWithEagerRelationships(Pageable pageable) {
        return registroRepository.findAllWithEagerRelationships(pageable).map(registroMapper::toDto);
    }
    

    /**
     * Get one registro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RegistroDTO> findOne(Long id) {
        log.debug("Request to get Registro : {}", id);
        return registroRepository.findOneWithEagerRelationships(id)
            .map(registroMapper::toDto);
    }

    /**
     * Delete the registro by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Registro : {}", id);
        registroRepository.deleteById(id);
    }
}
