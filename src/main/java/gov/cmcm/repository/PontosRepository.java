package gov.cmcm.repository;

import gov.cmcm.domain.Pontos;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pontos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PontosRepository extends JpaRepository<Pontos, Long> {
    void deleteBySpatialUnitId(Long spatialUnitId);

    List<Pontos> findBySpatialUnitId(Long spatialUnitId);
}
