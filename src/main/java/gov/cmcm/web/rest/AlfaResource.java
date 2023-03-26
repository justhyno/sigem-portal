package gov.cmcm.web.rest;

import gov.cmcm.repository.AlfaRepository;
import gov.cmcm.service.AlfaQueryService;
import gov.cmcm.service.AlfaService;
import gov.cmcm.service.criteria.AlfaCriteria;
import gov.cmcm.service.dto.AlfaDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gov.cmcm.domain.Alfa}.
 */
@RestController
@RequestMapping("/api")
public class AlfaResource {

    private final Logger log = LoggerFactory.getLogger(AlfaResource.class);

    private static final String ENTITY_NAME = "alfa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlfaService alfaService;

    private final AlfaRepository alfaRepository;

    private final AlfaQueryService alfaQueryService;

    public AlfaResource(AlfaService alfaService, AlfaRepository alfaRepository, AlfaQueryService alfaQueryService) {
        this.alfaService = alfaService;
        this.alfaRepository = alfaRepository;
        this.alfaQueryService = alfaQueryService;
    }

    /**
     * {@code POST  /alfas} : Create a new alfa.
     *
     * @param alfaDTO the alfaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alfaDTO, or with status {@code 400 (Bad Request)} if the alfa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alfas")
    public ResponseEntity<AlfaDTO> createAlfa(@RequestBody AlfaDTO alfaDTO) throws URISyntaxException {
        log.debug("REST request to save Alfa : {}", alfaDTO);
        if (alfaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alfa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlfaDTO result = alfaService.save(alfaDTO);
        return ResponseEntity
            .created(new URI("/api/alfas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alfas/:id} : Updates an existing alfa.
     *
     * @param id the id of the alfaDTO to save.
     * @param alfaDTO the alfaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alfaDTO,
     * or with status {@code 400 (Bad Request)} if the alfaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alfaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alfas/{id}")
    public ResponseEntity<AlfaDTO> updateAlfa(@PathVariable(value = "id", required = false) final Long id, @RequestBody AlfaDTO alfaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Alfa : {}, {}", id, alfaDTO);
        if (alfaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alfaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alfaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlfaDTO result = alfaService.update(alfaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alfaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alfas/:id} : Partial updates given fields of an existing alfa, field will ignore if it is null
     *
     * @param id the id of the alfaDTO to save.
     * @param alfaDTO the alfaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alfaDTO,
     * or with status {@code 400 (Bad Request)} if the alfaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alfaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alfaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alfas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlfaDTO> partialUpdateAlfa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlfaDTO alfaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Alfa partially : {}, {}", id, alfaDTO);
        if (alfaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alfaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alfaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlfaDTO> result = alfaService.partialUpdate(alfaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alfaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alfas} : get all the alfas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alfas in body.
     */
    @GetMapping("/alfas")
    public ResponseEntity<List<AlfaDTO>> getAllAlfas(
        AlfaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Alfas by criteria: {}", criteria);
        Page<AlfaDTO> page = alfaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alfas/count} : count all the alfas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alfas/count")
    public ResponseEntity<Long> countAlfas(AlfaCriteria criteria) {
        log.debug("REST request to count Alfas by criteria: {}", criteria);
        return ResponseEntity.ok().body(alfaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alfas/:id} : get the "id" alfa.
     *
     * @param id the id of the alfaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alfaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alfas/{id}")
    public ResponseEntity<AlfaDTO> getAlfa(@PathVariable Long id) {
        log.debug("REST request to get Alfa : {}", id);
        Optional<AlfaDTO> alfaDTO = alfaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alfaDTO);
    }

    /**
     * {@code DELETE  /alfas/:id} : delete the "id" alfa.
     *
     * @param id the id of the alfaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alfas/{id}")
    public ResponseEntity<Void> deleteAlfa(@PathVariable Long id) {
        log.debug("REST request to delete Alfa : {}", id);
        alfaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
