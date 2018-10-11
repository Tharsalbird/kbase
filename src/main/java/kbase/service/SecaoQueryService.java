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

import kbase.domain.Secao;
import kbase.domain.*; // for static metamodels
import kbase.repository.SecaoRepository;
import kbase.service.dto.SecaoCriteria;

import kbase.service.dto.SecaoDTO;
import kbase.service.mapper.SecaoMapper;

/**
 * Service for executing complex queries for Secao entities in the database.
 * The main input is a {@link SecaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SecaoDTO} or a {@link Page} of {@link SecaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecaoQueryService extends QueryService<Secao> {

    private final Logger log = LoggerFactory.getLogger(SecaoQueryService.class);

    private final SecaoRepository secaoRepository;

    private final SecaoMapper secaoMapper;

    public SecaoQueryService(SecaoRepository secaoRepository, SecaoMapper secaoMapper) {
        this.secaoRepository = secaoRepository;
        this.secaoMapper = secaoMapper;
    }

    /**
     * Return a {@link List} of {@link SecaoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SecaoDTO> findByCriteria(SecaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Secao> specification = createSpecification(criteria);
        return secaoMapper.toDto(secaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SecaoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecaoDTO> findByCriteria(SecaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Secao> specification = createSpecification(criteria);
        return secaoRepository.findAll(specification, page)
            .map(secaoMapper::toDto);
    }

    /**
     * Function to convert SecaoCriteria to a {@link Specification}
     */
    private Specification<Secao> createSpecification(SecaoCriteria criteria) {
        Specification<Secao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Secao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Secao_.nome));
            }
            if (criteria.getModelo() != null) {
                specification = specification.and(buildSpecification(criteria.getModelo(), Secao_.modelo));
            }
            if (criteria.getEditavel() != null) {
                specification = specification.and(buildSpecification(criteria.getEditavel(), Secao_.editavel));
            }
            if (criteria.getDataCriacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCriacao(), Secao_.dataCriacao));
            }
        }
        return specification;
    }

}
