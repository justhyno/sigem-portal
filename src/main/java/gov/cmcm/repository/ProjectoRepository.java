package gov.cmcm.repository;

import gov.cmcm.domain.Projecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectoRepository extends JpaRepository<Projecto, Long>, JpaSpecificationExecutor<Projecto> {}
