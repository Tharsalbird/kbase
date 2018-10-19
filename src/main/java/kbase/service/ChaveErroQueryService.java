package kbase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import kbase.domain.ChaveErro;
import kbase.domain.*; // for static metamodels
import kbase.repository.ChaveErroRepository;
import kbase.service.dto.ChaveErroCriteria;

import kbase.service.dto.ChaveErroDTO;
import kbase.service.mapper.ChaveErroMapper;

/**
 * Service for executing complex queries for ChaveErro entities in the database.
 * The main input is a {@link ChaveErroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChaveErroDTO} or a {@link Page} of {@link ChaveErroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChaveErroQueryService extends QueryService<ChaveErro> {

    private final Logger log = LoggerFactory.getLogger(ChaveErroQueryService.class);

    private final ChaveErroRepository chaveErroRepository;

    private final ChaveErroMapper chaveErroMapper;

    public ChaveErroQueryService(ChaveErroRepository chaveErroRepository, ChaveErroMapper chaveErroMapper) {
        this.chaveErroRepository = chaveErroRepository;
        this.chaveErroMapper = chaveErroMapper;
    }

    /**
     * Return a {@link List} of {@link ChaveErroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChaveErroDTO> findByCriteria(ChaveErroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChaveErro> specification = createSpecification(criteria);
        return chaveErroMapper.toDto(chaveErroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChaveErroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChaveErroDTO> findByCriteria(ChaveErroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChaveErro> specification = createSpecification(criteria);
        return chaveErroRepository.findAll(specification, page)
            .map(chaveErroMapper::toDto);
    }

    /**
     * Function to convert ChaveErroCriteria to a {@link Specification}
     */
    private Specification<ChaveErro> createSpecification(ChaveErroCriteria criteria) {
        Specification<ChaveErro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChaveErro_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), ChaveErro_.titulo));
            }
        }
        return specification;
    }

}
