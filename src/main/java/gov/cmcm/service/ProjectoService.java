package gov.cmcm.service;

import gov.cmcm.service.dto.ProjectoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gov.cmcm.domain.Projecto}.
 */
public interface ProjectoService {
    /**
     * Save a projecto.
     *
     * @param projectoDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectoDTO save(ProjectoDTO projectoDTO);

    /**
     * Updates a projecto.
     *
     * @param projectoDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectoDTO update(ProjectoDTO projectoDTO);

    /**
     * Partially updates a projecto.
     *
     * @param projectoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectoDTO> partialUpdate(ProjectoDTO projectoDTO);

    /**
     * Get all the projectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectoDTO> findOne(Long id);

    /**
     * Delete the "id" projecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
