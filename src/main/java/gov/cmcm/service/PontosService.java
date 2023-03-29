package gov.cmcm.service;

import gov.cmcm.service.dto.PontosDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gov.cmcm.domain.Pontos}.
 */
public interface PontosService {
    /**
     * Save a pontos.
     *
     * @param pontosDTO the entity to save.
     * @return the persisted entity.
     */
    PontosDTO save(PontosDTO pontosDTO);

    PontosDTO save(PontosDTO pontosDTO, Long projecto);

    /**
     * Updates a pontos.
     *
     * @param pontosDTO the entity to update.
     * @return the persisted entity.
     */
    PontosDTO update(PontosDTO pontosDTO);

    /**
     * Partially updates a pontos.
     *
     * @param pontosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PontosDTO> partialUpdate(PontosDTO pontosDTO);

    /**
     * Get all the pontos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PontosDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pontos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PontosDTO> findOne(Long id);

    /**
     * Delete the "id" pontos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public void deleteBySpu(HashMap<String, Long> map);

    Optional<List<PontosDTO>> findByFicha(Long ficha);
}
