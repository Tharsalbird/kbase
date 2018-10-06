package kbase.service;

import kbase.service.dto.RotuloDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Rotulo.
 */
public interface RotuloService {

    /**
     * Save a rotulo.
     *
     * @param rotuloDTO the entity to save
     * @return the persisted entity
     */
    RotuloDTO save(RotuloDTO rotuloDTO);

    /**
     * Get all the rotulos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RotuloDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rotulo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RotuloDTO> findOne(Long id);

    /**
     * Delete the "id" rotulo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
