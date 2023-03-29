package gov.cmcm.service;

import gov.cmcm.service.dto.FichaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gov.cmcm.domain.Ficha}.
 */
public interface FichaService {
    /**
     * Save a ficha.
     *
     * @param fichaDTO the entity to save.
     * @return the persisted entity.
     */
    FichaDTO save(FichaDTO fichaDTO);

    /**
     * Updates a ficha.
     *
     * @param fichaDTO the entity to update.
     * @return the persisted entity.
     */
    FichaDTO update(FichaDTO fichaDTO);

    /**
     * Partially updates a ficha.
     *
     * @param fichaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FichaDTO> partialUpdate(FichaDTO fichaDTO);

    /**
     * Get all the fichas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FichaDTO> findAll(Pageable pageable);

    /**
     * Get all the fichas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FichaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ficha.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaDTO> findOne(Long id);

    /**
     * Delete the "id" ficha.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    FichaDTO findByProjectAndParcel(Long project, String parcel);

    List<FichaDTO> findByProject(Long project);
}
