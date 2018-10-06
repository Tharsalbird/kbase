package kbase.web.rest;

import com.codahale.metrics.annotation.Timed;
import kbase.service.GlossarioService;
import kbase.web.rest.errors.BadRequestAlertException;
import kbase.web.rest.util.HeaderUtil;
import kbase.web.rest.util.PaginationUtil;
import kbase.service.dto.GlossarioDTO;
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
 * REST controller for managing Glossario.
 */
@RestController
@RequestMapping("/api")
public class GlossarioResource {

    private final Logger log = LoggerFactory.getLogger(GlossarioResource.class);

    private static final String ENTITY_NAME = "glossario";

    private final GlossarioService glossarioService;

    public GlossarioResource(GlossarioService glossarioService) {
        this.glossarioService = glossarioService;
    }

    /**
     * POST  /glossarios : Create a new glossario.
     *
     * @param glossarioDTO the glossarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossarioDTO, or with status 400 (Bad Request) if the glossario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossarios")
    @Timed
    public ResponseEntity<GlossarioDTO> createGlossario(@Valid @RequestBody GlossarioDTO glossarioDTO) throws URISyntaxException {
        log.debug("REST request to save Glossario : {}", glossarioDTO);
        if (glossarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new glossario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossarioDTO result = glossarioService.save(glossarioDTO);
        return ResponseEntity.created(new URI("/api/glossarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossarios : Updates an existing glossario.
     *
     * @param glossarioDTO the glossarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossarioDTO,
     * or with status 400 (Bad Request) if the glossarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the glossarioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossarios")
    @Timed
    public ResponseEntity<GlossarioDTO> updateGlossario(@Valid @RequestBody GlossarioDTO glossarioDTO) throws URISyntaxException {
        log.debug("REST request to update Glossario : {}", glossarioDTO);
        if (glossarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlossarioDTO result = glossarioService.save(glossarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossarios : get all the glossarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossarios in body
     */
    @GetMapping("/glossarios")
    @Timed
    public ResponseEntity<List<GlossarioDTO>> getAllGlossarios(Pageable pageable) {
        log.debug("REST request to get a page of Glossarios");
        Page<GlossarioDTO> page = glossarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /glossarios/:id : get the "id" glossario.
     *
     * @param id the id of the glossarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/glossarios/{id}")
    @Timed
    public ResponseEntity<GlossarioDTO> getGlossario(@PathVariable Long id) {
        log.debug("REST request to get Glossario : {}", id);
        Optional<GlossarioDTO> glossarioDTO = glossarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(glossarioDTO);
    }

    /**
     * DELETE  /glossarios/:id : delete the "id" glossario.
     *
     * @param id the id of the glossarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossario(@PathVariable Long id) {
        log.debug("REST request to delete Glossario : {}", id);
        glossarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
