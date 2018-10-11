package kbase.service;

import kbase.domain.Secao;
import kbase.repository.SecaoRepository;
import kbase.service.dto.SecaoDTO;
import kbase.service.mapper.SecaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Secao.
 */
@Service
@Transactional
public class SecaoService {

    private final Logger log = LoggerFactory.getLogger(SecaoService.class);

    private final SecaoRepository secaoRepository;

    private final SecaoMapper secaoMapper;

    public SecaoService(SecaoRepository secaoRepository, SecaoMapper secaoMapper) {
        this.secaoRepository = secaoRepository;
        this.secaoMapper = secaoMapper;
    }

    /**
     * Save a secao.
     *
     * @param secaoDTO the entity to save
     * @return the persisted entity
     */
    public SecaoDTO save(SecaoDTO secaoDTO) {
        log.debug("Request to save Secao : {}", secaoDTO);
        Secao secao = secaoMapper.toEntity(secaoDTO);
        secao = secaoRepository.save(secao);
        return secaoMapper.toDto(secao);
    }

    /**
     * Get all the secaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SecaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Secaos");
        return secaoRepository.findAll(pageable)
            .map(secaoMapper::toDto);
    }


    /**
     * Get one secao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SecaoDTO> findOne(Long id) {
        log.debug("Request to get Secao : {}", id);
        return secaoRepository.findById(id)
            .map(secaoMapper::toDto);
    }

    /**
     * Delete the secao by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Secao : {}", id);
        secaoRepository.deleteById(id);
    }
}
