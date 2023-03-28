package gov.cmcm.service;

import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.ProjectoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gov.cmcm.domain.Alfa}.
 */
public interface AlfaService {
    /**
     * Save a alfa.
     *
     * @param alfaDTO the entity to save.
     * @return the persisted entity.
     */
    AlfaDTO save(AlfaDTO alfaDTO);

    AlfaDTO save(AlfaDTO alfaDTO, ProjectoDTO projectoDTO);

    /**
     * Updates a alfa.
     *
     * @param alfaDTO the entity to update.
     * @return the persisted entity.
     */
    AlfaDTO update(AlfaDTO alfaDTO);

    /**
     * Partially updates a alfa.
     *
     * @param alfaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlfaDTO> partialUpdate(AlfaDTO alfaDTO);

    /**
     * Get all the alfas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlfaDTO> findAll(Pageable pageable);

    /**
     * Get all the alfas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlfaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" alfa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlfaDTO> findOne(Long id);

    /**
     * Delete the "id" alfa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
