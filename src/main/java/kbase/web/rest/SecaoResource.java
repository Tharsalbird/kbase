package kbase.web.rest;

import com.codahale.metrics.annotation.Timed;
import kbase.service.SecaoService;
import kbase.web.rest.errors.BadRequestAlertException;
import kbase.web.rest.util.HeaderUtil;
import kbase.web.rest.util.PaginationUtil;
import kbase.service.dto.SecaoDTO;
import kbase.service.dto.SecaoCriteria;
import kbase.service.SecaoQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Secao.
 */
@RestController
@RequestMapping("/api")
public class SecaoResource {

    private final Logger log = LoggerFactory.getLogger(SecaoResource.class);

    private static final String ENTITY_NAME = "secao";

    private final SecaoService secaoService;

    private final SecaoQueryService secaoQueryService;

    public SecaoResource(SecaoService secaoService, SecaoQueryService secaoQueryService) {
        this.secaoService = secaoService;
        this.secaoQueryService = secaoQueryService;
    }

    /**
     * POST  /secaos : Create a new secao.
     *
     * @param secaoDTO the secaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secaoDTO, or with status 400 (Bad Request) if the secao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/secaos")
    @Timed
    public ResponseEntity<SecaoDTO> createSecao(@Valid @RequestBody SecaoDTO secaoDTO) throws URISyntaxException {
        log.debug("REST request to save Secao : {}", secaoDTO);
        if (secaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new secao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecaoDTO result = secaoService.save(secaoDTO);
        return ResponseEntity.created(new URI("/api/secaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secaos : Updates an existing secao.
     *
     * @param secaoDTO the secaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secaoDTO,
     * or with status 400 (Bad Request) if the secaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the secaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/secaos")
    @Timed
    public ResponseEntity<SecaoDTO> updateSecao(@Valid @RequestBody SecaoDTO secaoDTO) throws URISyntaxException {
        log.debug("REST request to update Secao : {}", secaoDTO);
        if (secaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SecaoDTO result = secaoService.save(secaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secaos : get all the secaos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of secaos in body
     */
    @GetMapping("/secaos")
    @Timed
    public ResponseEntity<List<SecaoDTO>> getAllSecaos(SecaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Secaos by criteria: {}", criteria);
        Page<SecaoDTO> page = secaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/secaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /secaos/:id : get the "id" secao.
     *
     * @param id the id of the secaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/secaos/{id}")
    @Timed
    public ResponseEntity<SecaoDTO> getSecao(@PathVariable Long id) {
        log.debug("REST request to get Secao : {}", id);
        Optional<SecaoDTO> secaoDTO = secaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(secaoDTO);
    }

    /**
     * DELETE  /secaos/:id : delete the "id" secao.
     *
     * @param id the id of the secaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/secaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecao(@PathVariable Long id) {
        log.debug("REST request to delete Secao : {}", id);
        secaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
