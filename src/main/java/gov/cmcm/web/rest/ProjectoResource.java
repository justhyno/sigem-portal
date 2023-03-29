package gov.cmcm.web.rest;

import gov.cmcm.repository.ProjectoRepository;
import gov.cmcm.service.ProjectoQueryService;
import gov.cmcm.service.ProjectoService;
import gov.cmcm.service.criteria.ProjectoCriteria;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * REST controller for managing {@link gov.cmcm.domain.Projecto}.
 */
@RestController
@RequestMapping("/api")
public class ProjectoResource {

    private final Logger log = LoggerFactory.getLogger(ProjectoResource.class);

    private static final String ENTITY_NAME = "projecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectoService projectoService;

    private final ProjectoRepository projectoRepository;

    private final ProjectoQueryService projectoQueryService;

    public ProjectoResource(
        ProjectoService projectoService,
        ProjectoRepository projectoRepository,
        ProjectoQueryService projectoQueryService
    ) {
        this.projectoService = projectoService;
        this.projectoRepository = projectoRepository;
        this.projectoQueryService = projectoQueryService;
    }

    /**
     * {@code POST  /projectos} : Create a new projecto.
     *
     * @param projectoDTO the projectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new projectoDTO, or with status {@code 400 (Bad Request)} if
     *         the projecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projectos")
    public ResponseEntity<ProjectoDTO> createProjecto(@RequestBody ProjectoDTO projectoDTO) throws URISyntaxException {
        log.debug("REST request to save Projecto : {}", projectoDTO);
        if (projectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new projecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectoDTO result = projectoService.save(projectoDTO);
        return ResponseEntity
            .created(new URI("/api/projectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projectos/:id} : Updates an existing projecto.
     *
     * @param id          the id of the projectoDTO to save.
     * @param projectoDTO the projectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated projectoDTO,
     *         or with status {@code 400 (Bad Request)} if the projectoDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the projectoDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projectos/{id}")
    public ResponseEntity<ProjectoDTO> updateProjecto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectoDTO projectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Projecto : {}, {}", id, projectoDTO);
        if (projectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectoDTO result = projectoService.update(projectoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projectos/:id} : Partial updates given fields of an existing
     * projecto, field will ignore if it is null
     *
     * @param id          the id of the projectoDTO to save.
     * @param projectoDTO the projectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated projectoDTO,
     *         or with status {@code 400 (Bad Request)} if the projectoDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the projectoDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the projectoDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/projectos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectoDTO> partialUpdateProjecto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectoDTO projectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projecto partially : {}, {}", id, projectoDTO);
        if (projectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectoDTO> result = projectoService.partialUpdate(projectoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /projectos} : get all the projectos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of projectos in body.
     */
    @GetMapping("/projectos")
    public ResponseEntity<List<ProjectoDTO>> getAllProjectos(
        ProjectoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Projectos by criteria: {}", criteria);
        Page<ProjectoDTO> page = projectoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projectos/count} : count all the projectos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/projectos/count")
    public ResponseEntity<Long> countProjectos(ProjectoCriteria criteria) {
        log.debug("REST request to count Projectos by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /projectos/:id} : get the "id" projecto.
     *
     * @param id the id of the projectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the projectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projectos/{id}")
    public ResponseEntity<ProjectoDTO> getProjecto(@PathVariable Long id) {
        log.debug("REST request to get Projecto : {}", id);
        Optional<ProjectoDTO> projectoDTO = projectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectoDTO);
    }

    @GetMapping("/projectos/titulos/{projectId}")
    public ResponseEntity<Map<String, String>> generateTitulos(@PathVariable Long projectId) {
        log.info("REST request to generate titulos for project: {}", projectId);

        try {
            projectoService.generateTitulo(projectId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Titulos generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to generate titulos");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * {@code DELETE  /projectos/:id} : delete the "id" projecto.
     *
     * @param id the id of the projectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projectos/{id}")
    public ResponseEntity<Void> deleteProjecto(@PathVariable Long id) {
        log.debug("REST request to delete Projecto : {}", id);
        System.out.println("ProjectoResource.deleteProjecto()");
        projectoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
