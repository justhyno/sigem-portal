package gov.cmcm.repository;

import gov.cmcm.domain.Ficha;
import gov.cmcm.service.dto.FichaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ficha entity.
 */
@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long>, JpaSpecificationExecutor<Ficha> {
    default Optional<Ficha> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Ficha> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Ficha> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct ficha from Ficha ficha left join fetch ficha.projecto",
        countQuery = "select count(distinct ficha) from Ficha ficha"
    )
    Page<Ficha> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct ficha from Ficha ficha left join fetch ficha.projecto")
    List<Ficha> findAllWithToOneRelationships();

    @Query("select ficha from Ficha ficha left join fetch ficha.projecto where ficha.id =:id")
    Optional<Ficha> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select distinct ficha from Ficha ficha where parcela like ?2 and projecto.id = ?1")
    Ficha findByProjectAndParcel(long projecto, String parcela);

    @Query(value = "SELECT nextval(?1)", nativeQuery = true)
    Long getNextVal(String sequenceName);

    List<Ficha> findByProjectoId(Long projectoId);
}
