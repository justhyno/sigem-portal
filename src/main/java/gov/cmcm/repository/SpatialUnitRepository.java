package gov.cmcm.repository;

import gov.cmcm.domain.SpatialUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SpatialUnit entity.
 */
@Repository
public interface SpatialUnitRepository extends JpaRepository<SpatialUnit, Long>, JpaSpecificationExecutor<SpatialUnit> {
    default Optional<SpatialUnit> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SpatialUnit> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SpatialUnit> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct spatialUnit from SpatialUnit spatialUnit left join fetch spatialUnit.ficha",
        countQuery = "select count(distinct spatialUnit) from SpatialUnit spatialUnit"
    )
    Page<SpatialUnit> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct spatialUnit from SpatialUnit spatialUnit left join fetch spatialUnit.ficha")
    List<SpatialUnit> findAllWithToOneRelationships();

    @Query("select spatialUnit from SpatialUnit spatialUnit left join fetch spatialUnit.ficha where spatialUnit.id =:id")
    Optional<SpatialUnit> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select spatialUnit from SpatialUnit spatialUnit where spatialUnit.ficha.id=?1")
    Optional<SpatialUnit> getByProjectoByFicha(long id);

    Optional<SpatialUnit> findByFichaId(Long ficha);
}
