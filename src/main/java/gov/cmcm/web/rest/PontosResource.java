package gov.cmcm.web.rest;

import gov.cmcm.repository.PontosRepository;
import gov.cmcm.service.PontosService;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gov.cmcm.domain.Pontos}.
 */
@RestController
@RequestMapping("/api")
public class PontosResource {

    private final Logger log = LoggerFactory.getLogger(PontosResource.class);

    private static final String ENTITY_NAME = "pontos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PontosService pontosService;

    private final PontosRepository pontosRepository;

    public PontosResource(PontosService pontosService, PontosRepository pontosRepository) {
        this.pontosService = pontosService;
        this.pontosRepository = pontosRepository;
    }

    /**
     * {@code POST  /pontos} : Create a new pontos.
     *
     * @param pontosDTO the pontosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pontosDTO, or with status {@code 400 (Bad Request)} if the pontos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pontos")
    public ResponseEntity<PontosDTO> createPontos(@RequestBody PontosDTO pontosDTO) throws URISyntaxException {
        log.debug("REST request to save Pontos : {}", pontosDTO);
        if (pontosDTO.getId() != null) {
            throw new BadRequestAlertException("A new pontos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PontosDTO result = pontosService.save(pontosDTO);
        return ResponseEntity
            .created(new URI("/api/pontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pontos/:id} : Updates an existing pontos.
     *
     * @param id the id of the pontosDTO to save.
     * @param pontosDTO the pontosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pontosDTO,
     * or with status {@code 400 (Bad Request)} if the pontosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pontosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pontos/{id}")
    public ResponseEntity<PontosDTO> updatePontos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PontosDTO pontosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pontos : {}, {}", id, pontosDTO);
        if (pontosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pontosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pontosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PontosDTO result = pontosService.update(pontosDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pontosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pontos/:id} : Partial updates given fields of an existing pontos, field will ignore if it is null
     *
     * @param id the id of the pontosDTO to save.
     * @param pontosDTO the pontosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pontosDTO,
     * or with status {@code 400 (Bad Request)} if the pontosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pontosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pontosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pontos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PontosDTO> partialUpdatePontos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PontosDTO pontosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pontos partially : {}, {}", id, pontosDTO);
        if (pontosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pontosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pontosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PontosDTO> result = pontosService.partialUpdate(pontosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pontosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pontos} : get all the pontos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pontos in body.
     */
    @GetMapping("/pontos")
    public ResponseEntity<List<PontosDTO>> getAllPontos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Pontos");
        Page<PontosDTO> page = pontosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pontos/:id} : get the "id" pontos.
     *
     * @param id the id of the pontosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pontosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pontos/{id}")
    public ResponseEntity<PontosDTO> getPontos(@PathVariable Long id) {
        log.debug("REST request to get Pontos : {}", id);
        Optional<PontosDTO> pontosDTO = pontosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pontosDTO);
    }

    /**
     * {@code DELETE  /pontos/:id} : delete the "id" pontos.
     *
     * @param id the id of the pontosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pontos/{id}")
    public ResponseEntity<Void> deletePontos(@PathVariable Long id) {
        log.debug("REST request to delete Pontos : {}", id);
        pontosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
