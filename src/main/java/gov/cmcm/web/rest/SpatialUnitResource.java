package gov.cmcm.web.rest;

import gov.cmcm.repository.SpatialUnitRepository;
import gov.cmcm.service.SpatialUnitQueryService;
import gov.cmcm.service.SpatialUnitService;
import gov.cmcm.service.criteria.SpatialUnitCriteria;
import gov.cmcm.service.dto.SpatialUnitDTO;
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
 * REST controller for managing {@link gov.cmcm.domain.SpatialUnit}.
 */
@RestController
@RequestMapping("/api")
public class SpatialUnitResource {

    private final Logger log = LoggerFactory.getLogger(SpatialUnitResource.class);

    private static final String ENTITY_NAME = "spatialUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpatialUnitService spatialUnitService;

    private final SpatialUnitRepository spatialUnitRepository;

    private final SpatialUnitQueryService spatialUnitQueryService;

    public SpatialUnitResource(
        SpatialUnitService spatialUnitService,
        SpatialUnitRepository spatialUnitRepository,
        SpatialUnitQueryService spatialUnitQueryService
    ) {
        this.spatialUnitService = spatialUnitService;
        this.spatialUnitRepository = spatialUnitRepository;
        this.spatialUnitQueryService = spatialUnitQueryService;
    }

    /**
     * {@code POST  /spatial-units} : Create a new spatialUnit.
     *
     * @param spatialUnitDTO the spatialUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spatialUnitDTO, or with status {@code 400 (Bad Request)} if the spatialUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spatial-units")
    public ResponseEntity<SpatialUnitDTO> createSpatialUnit(@RequestBody SpatialUnitDTO spatialUnitDTO) throws URISyntaxException {
        log.debug("REST request to save SpatialUnit : {}", spatialUnitDTO);
        if (spatialUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new spatialUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpatialUnitDTO result = spatialUnitService.save(spatialUnitDTO);
        return ResponseEntity
            .created(new URI("/api/spatial-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spatial-units/:id} : Updates an existing spatialUnit.
     *
     * @param id the id of the spatialUnitDTO to save.
     * @param spatialUnitDTO the spatialUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spatialUnitDTO,
     * or with status {@code 400 (Bad Request)} if the spatialUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spatialUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spatial-units/{id}")
    public ResponseEntity<SpatialUnitDTO> updateSpatialUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpatialUnitDTO spatialUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SpatialUnit : {}, {}", id, spatialUnitDTO);
        if (spatialUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spatialUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spatialUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpatialUnitDTO result = spatialUnitService.update(spatialUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spatialUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /spatial-units/:id} : Partial updates given fields of an existing spatialUnit, field will ignore if it is null
     *
     * @param id the id of the spatialUnitDTO to save.
     * @param spatialUnitDTO the spatialUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spatialUnitDTO,
     * or with status {@code 400 (Bad Request)} if the spatialUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the spatialUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the spatialUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/spatial-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpatialUnitDTO> partialUpdateSpatialUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpatialUnitDTO spatialUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpatialUnit partially : {}, {}", id, spatialUnitDTO);
        if (spatialUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spatialUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spatialUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpatialUnitDTO> result = spatialUnitService.partialUpdate(spatialUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spatialUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /spatial-units} : get all the spatialUnits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spatialUnits in body.
     */
    @GetMapping("/spatial-units")
    public ResponseEntity<List<SpatialUnitDTO>> getAllSpatialUnits(
        SpatialUnitCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SpatialUnits by criteria: {}", criteria);
        Page<SpatialUnitDTO> page = spatialUnitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /spatial-units/count} : count all the spatialUnits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/spatial-units/count")
    public ResponseEntity<Long> countSpatialUnits(SpatialUnitCriteria criteria) {
        log.debug("REST request to count SpatialUnits by criteria: {}", criteria);
        return ResponseEntity.ok().body(spatialUnitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /spatial-units/:id} : get the "id" spatialUnit.
     *
     * @param id the id of the spatialUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spatialUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spatial-units/{id}")
    public ResponseEntity<SpatialUnitDTO> getSpatialUnit(@PathVariable Long id) {
        log.debug("REST request to get SpatialUnit : {}", id);
        Optional<SpatialUnitDTO> spatialUnitDTO = spatialUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spatialUnitDTO);
    }

    /**
     * {@code DELETE  /spatial-units/:id} : delete the "id" spatialUnit.
     *
     * @param id the id of the spatialUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spatial-units/{id}")
    public ResponseEntity<Void> deleteSpatialUnit(@PathVariable Long id) {
        log.debug("REST request to delete SpatialUnit : {}", id);
        spatialUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
