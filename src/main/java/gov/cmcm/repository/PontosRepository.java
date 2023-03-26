package gov.cmcm.repository;

import gov.cmcm.domain.Pontos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pontos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PontosRepository extends JpaRepository<Pontos, Long> {}
