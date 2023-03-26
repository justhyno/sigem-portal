package gov.cmcm.repository;

import gov.cmcm.domain.Alfa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Alfa entity.
 */
@Repository
public interface AlfaRepository extends JpaRepository<Alfa, Long>, JpaSpecificationExecutor<Alfa> {
    default Optional<Alfa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Alfa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Alfa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct alfa from Alfa alfa left join fetch alfa.ficha",
        countQuery = "select count(distinct alfa) from Alfa alfa"
    )
    Page<Alfa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct alfa from Alfa alfa left join fetch alfa.ficha")
    List<Alfa> findAllWithToOneRelationships();

    @Query("select alfa from Alfa alfa left join fetch alfa.ficha where alfa.id =:id")
    Optional<Alfa> findOneWithToOneRelationships(@Param("id") Long id);
}
