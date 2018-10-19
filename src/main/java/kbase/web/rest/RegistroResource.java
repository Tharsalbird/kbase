package kbase.web.rest;

import com.codahale.metrics.annotation.Timed;
import kbase.service.RegistroService;
import kbase.web.rest.errors.BadRequestAlertException;
import kbase.web.rest.util.HeaderUtil;
import kbase.web.rest.util.PaginationUtil;
import kbase.service.dto.RegistroDTO;
import kbase.service.dto.RegistroCriteria;
import kbase.service.RegistroQueryService;
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
 * REST controller for managing Registro.
 */
@RestController
@RequestMapping("/api")
public class RegistroResource {

    private final Logger log = LoggerFactory.getLogger(RegistroResource.class);

    private static final String ENTITY_NAME = "registro";

    private final RegistroService registroService;

    private final RegistroQueryService registroQueryService;

    public RegistroResource(RegistroService registroService, RegistroQueryService registroQueryService) {
        this.registroService = registroService;
        this.registroQueryService = registroQueryService;
    }

    /**
     * POST  /registros : Create a new registro.
     *
     * @param registroDTO the registroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registroDTO, or with status 400 (Bad Request) if the registro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registros")
    @Timed
    public ResponseEntity<RegistroDTO> createRegistro(@Valid @RequestBody RegistroDTO registroDTO) throws URISyntaxException {
        log.debug("REST request to save Registro : {}", registroDTO);
        if (registroDTO.getId() != null) {
            throw new BadRequestAlertException("A new registro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroDTO result = registroService.save(registroDTO);
        return ResponseEntity.created(new URI("/api/registros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registros : Updates an existing registro.
     *
     * @param registroDTO the registroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registroDTO,
     * or with status 400 (Bad Request) if the registroDTO is not valid,
     * or with status 500 (Internal Server Error) if the registroDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registros")
    @Timed
    public ResponseEntity<RegistroDTO> updateRegistro(@Valid @RequestBody RegistroDTO registroDTO) throws URISyntaxException {
        log.debug("REST request to update Registro : {}", registroDTO);
        if (registroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistroDTO result = registroService.save(registroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registros : get all the registros.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of registros in body
     */
    @GetMapping("/registros")
    @Timed
    public ResponseEntity<List<RegistroDTO>> getAllRegistros(RegistroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Registros by criteria: {}", criteria);
        Page<RegistroDTO> page = registroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /registros/:id : get the "id" registro.
     *
     * @param id the id of the registroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/registros/{id}")
    @Timed
    public ResponseEntity<RegistroDTO> getRegistro(@PathVariable Long id) {
        log.debug("REST request to get Registro : {}", id);
        Optional<RegistroDTO> registroDTO = registroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroDTO);
    }

    /**
     * DELETE  /registros/:id : delete the "id" registro.
     *
     * @param id the id of the registroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registros/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {
        log.debug("REST request to delete Registro : {}", id);
        registroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
