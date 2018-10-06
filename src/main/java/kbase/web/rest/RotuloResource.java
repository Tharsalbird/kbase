package kbase.web.rest;

import com.codahale.metrics.annotation.Timed;
import kbase.service.RotuloService;
import kbase.web.rest.errors.BadRequestAlertException;
import kbase.web.rest.util.HeaderUtil;
import kbase.web.rest.util.PaginationUtil;
import kbase.service.dto.RotuloDTO;
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
 * REST controller for managing Rotulo.
 */
@RestController
@RequestMapping("/api")
public class RotuloResource {

    private final Logger log = LoggerFactory.getLogger(RotuloResource.class);

    private static final String ENTITY_NAME = "rotulo";

    private final RotuloService rotuloService;

    public RotuloResource(RotuloService rotuloService) {
        this.rotuloService = rotuloService;
    }

    /**
     * POST  /rotulos : Create a new rotulo.
     *
     * @param rotuloDTO the rotuloDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rotuloDTO, or with status 400 (Bad Request) if the rotulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rotulos")
    @Timed
    public ResponseEntity<RotuloDTO> createRotulo(@Valid @RequestBody RotuloDTO rotuloDTO) throws URISyntaxException {
        log.debug("REST request to save Rotulo : {}", rotuloDTO);
        if (rotuloDTO.getId() != null) {
            throw new BadRequestAlertException("A new rotulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RotuloDTO result = rotuloService.save(rotuloDTO);
        return ResponseEntity.created(new URI("/api/rotulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rotulos : Updates an existing rotulo.
     *
     * @param rotuloDTO the rotuloDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rotuloDTO,
     * or with status 400 (Bad Request) if the rotuloDTO is not valid,
     * or with status 500 (Internal Server Error) if the rotuloDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rotulos")
    @Timed
    public ResponseEntity<RotuloDTO> updateRotulo(@Valid @RequestBody RotuloDTO rotuloDTO) throws URISyntaxException {
        log.debug("REST request to update Rotulo : {}", rotuloDTO);
        if (rotuloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RotuloDTO result = rotuloService.save(rotuloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rotuloDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rotulos : get all the rotulos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rotulos in body
     */
    @GetMapping("/rotulos")
    @Timed
    public ResponseEntity<List<RotuloDTO>> getAllRotulos(Pageable pageable) {
        log.debug("REST request to get a page of Rotulos");
        Page<RotuloDTO> page = rotuloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rotulos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rotulos/:id : get the "id" rotulo.
     *
     * @param id the id of the rotuloDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rotuloDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rotulos/{id}")
    @Timed
    public ResponseEntity<RotuloDTO> getRotulo(@PathVariable Long id) {
        log.debug("REST request to get Rotulo : {}", id);
        Optional<RotuloDTO> rotuloDTO = rotuloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rotuloDTO);
    }

    /**
     * DELETE  /rotulos/:id : delete the "id" rotulo.
     *
     * @param id the id of the rotuloDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rotulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRotulo(@PathVariable Long id) {
        log.debug("REST request to delete Rotulo : {}", id);
        rotuloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
