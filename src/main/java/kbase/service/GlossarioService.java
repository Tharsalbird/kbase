package kbase.service;

import kbase.service.dto.GlossarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Glossario.
 */
public interface GlossarioService {

    /**
     * Save a glossario.
     *
     * @param glossarioDTO the entity to save
     * @return the persisted entity
     */
    GlossarioDTO save(GlossarioDTO glossarioDTO);

    /**
     * Get all the glossarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GlossarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" glossario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GlossarioDTO> findOne(Long id);

    /**
     * Delete the "id" glossario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
