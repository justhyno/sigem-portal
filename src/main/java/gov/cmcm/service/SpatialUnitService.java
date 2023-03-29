package gov.cmcm.service;

import gov.cmcm.service.dto.SpatialUnitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gov.cmcm.domain.SpatialUnit}.
 */
public interface SpatialUnitService {
    /**
     * Save a spatialUnit.
     *
     * @param spatialUnitDTO the entity to save.
     * @return the persisted entity.
     */
    SpatialUnitDTO save(SpatialUnitDTO spatialUnitDTO);

    SpatialUnitDTO getByProjectoAndParcela(Long projecto, String parcela);

    /**
     * Updates a spatialUnit.
     *
     * @param spatialUnitDTO the entity to update.
     * @return the persisted entity.
     */
    SpatialUnitDTO update(SpatialUnitDTO spatialUnitDTO);

    /**
     * Partially updates a spatialUnit.
     *
     * @param spatialUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SpatialUnitDTO> partialUpdate(SpatialUnitDTO spatialUnitDTO);

    /**
     * Get all the spatialUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpatialUnitDTO> findAll(Pageable pageable);

    /**
     * Get all the spatialUnits with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpatialUnitDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" spatialUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpatialUnitDTO> findOne(Long id);

    /**
     * Delete the "id" spatialUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<SpatialUnitDTO> findByFichaId(Long ficha);
}
