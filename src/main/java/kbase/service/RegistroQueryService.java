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

import kbase.domain.Registro;
import kbase.domain.*; // for static metamodels
import kbase.repository.RegistroRepository;
import kbase.service.dto.RegistroCriteria;

import kbase.service.dto.RegistroDTO;
import kbase.service.mapper.RegistroMapper;

/**
 * Service for executing complex queries for Registro entities in the database.
 * The main input is a {@link RegistroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistroDTO} or a {@link Page} of {@link RegistroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistroQueryService extends QueryService<Registro> {

    private final Logger log = LoggerFactory.getLogger(RegistroQueryService.class);

    private final RegistroRepository registroRepository;

    private final RegistroMapper registroMapper;

    public RegistroQueryService(RegistroRepository registroRepository, RegistroMapper registroMapper) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
    }

    /**
     * Return a {@link List} of {@link RegistroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistroDTO> findByCriteria(RegistroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Registro> specification = createSpecification(criteria);
        return registroMapper.toDto(registroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegistroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroDTO> findByCriteria(RegistroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Registro> specification = createSpecification(criteria);
        return registroRepository.findAll(specification, page)
            .map(registroMapper::toDto);
    }

    /**
     * Function to convert RegistroCriteria to a {@link Specification}
     */
    private Specification<Registro> createSpecification(RegistroCriteria criteria) {
        Specification<Registro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Registro_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Registro_.titulo));
            }
            if (criteria.getSecaoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSecaoId(), Registro_.secao, Secao_.id));
            }
            if (criteria.getRotuloId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRotuloId(), Registro_.rotulos, Rotulo_.id));
            }
        }
        return specification;
    }

}
