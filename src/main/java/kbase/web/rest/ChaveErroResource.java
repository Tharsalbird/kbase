package kbase.web.rest;

import com.codahale.metrics.annotation.Timed;
import kbase.service.ChaveErroService;
import kbase.web.rest.errors.BadRequestAlertException;
import kbase.web.rest.util.HeaderUtil;
import kbase.web.rest.util.PaginationUtil;
import kbase.service.dto.ChaveErroDTO;
import kbase.service.dto.ChaveErroCriteria;
import kbase.service.ChaveErroQueryService;
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
 * REST controller for managing ChaveErro.
 */
@RestController
@RequestMapping("/api")
public class ChaveErroResource {

    private final Logger log = LoggerFactory.getLogger(ChaveErroResource.class);

    private static final String ENTITY_NAME = "chaveErro";

    private final ChaveErroService chaveErroService;

    private final ChaveErroQueryService chaveErroQueryService;

    public ChaveErroResource(ChaveErroService chaveErroService, ChaveErroQueryService chaveErroQueryService) {
        this.chaveErroService = chaveErroService;
        this.chaveErroQueryService = chaveErroQueryService;
    }

    /**
     * POST  /chave-erros : Create a new chaveErro.
     *
     * @param chaveErroDTO the chaveErroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chaveErroDTO, or with status 400 (Bad Request) if the chaveErro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chave-erros")
    @Timed
    public ResponseEntity<ChaveErroDTO> createChaveErro(@Valid @RequestBody ChaveErroDTO chaveErroDTO) throws URISyntaxException {
        log.debug("REST request to save ChaveErro : {}", chaveErroDTO);
        if (chaveErroDTO.getId() != null) {
            throw new BadRequestAlertException("A new chaveErro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChaveErroDTO result = chaveErroService.save(chaveErroDTO);
        return ResponseEntity.created(new URI("/api/chave-erros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chave-erros : Updates an existing chaveErro.
     *
     * @param chaveErroDTO the chaveErroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chaveErroDTO,
     * or with status 400 (Bad Request) if the chaveErroDTO is not valid,
     * or with status 500 (Internal Server Error) if the chaveErroDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chave-erros")
    @Timed
    public ResponseEntity<ChaveErroDTO> updateChaveErro(@Valid @RequestBody ChaveErroDTO chaveErroDTO) throws URISyntaxException {
        log.debug("REST request to update ChaveErro : {}", chaveErroDTO);
        if (chaveErroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChaveErroDTO result = chaveErroService.save(chaveErroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chaveErroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chave-erros : get all the chaveErros.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of chaveErros in body
     */
    @GetMapping("/chave-erros")
    @Timed
    public ResponseEntity<List<ChaveErroDTO>> getAllChaveErros(ChaveErroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ChaveErros by criteria: {}", criteria);
        Page<ChaveErroDTO> page = chaveErroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chave-erros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chave-erros/:id : get the "id" chaveErro.
     *
     * @param id the id of the chaveErroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chaveErroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/chave-erros/{id}")
    @Timed
    public ResponseEntity<ChaveErroDTO> getChaveErro(@PathVariable Long id) {
        log.debug("REST request to get ChaveErro : {}", id);
        Optional<ChaveErroDTO> chaveErroDTO = chaveErroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chaveErroDTO);
    }

    /**
     * DELETE  /chave-erros/:id : delete the "id" chaveErro.
     *
     * @param id the id of the chaveErroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chave-erros/{id}")
    @Timed
    public ResponseEntity<Void> deleteChaveErro(@PathVariable Long id) {
        log.debug("REST request to delete ChaveErro : {}", id);
        chaveErroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
