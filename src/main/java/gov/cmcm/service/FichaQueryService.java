package gov.cmcm.service;

import gov.cmcm.domain.*; // for static metamodels
import gov.cmcm.domain.Ficha;
import gov.cmcm.repository.FichaRepository;
import gov.cmcm.service.criteria.FichaCriteria;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.mapper.FichaMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Ficha} entities in the database.
 * The main input is a {@link FichaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FichaDTO} or a {@link Page} of {@link FichaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FichaQueryService extends QueryService<Ficha> {

    private final Logger log = LoggerFactory.getLogger(FichaQueryService.class);

    private final FichaRepository fichaRepository;

    private final FichaMapper fichaMapper;

    public FichaQueryService(FichaRepository fichaRepository, FichaMapper fichaMapper) {
        this.fichaRepository = fichaRepository;
        this.fichaMapper = fichaMapper;
    }

    /**
     * Return a {@link List} of {@link FichaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FichaDTO> findByCriteria(FichaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ficha> specification = createSpecification(criteria);
        return fichaMapper.toDto(fichaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FichaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FichaDTO> findByCriteria(FichaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ficha> specification = createSpecification(criteria);
        return fichaRepository.findAll(specification, page).map(fichaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FichaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ficha> specification = createSpecification(criteria);
        return fichaRepository.count(specification);
    }

    /**
     * Function to convert {@link FichaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ficha> createSpecification(FichaCriteria criteria) {
        Specification<Ficha> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ficha_.id));
            }
            if (criteria.getParcela() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParcela(), Ficha_.parcela));
            }
            if (criteria.getProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcesso(), Ficha_.processo));
            }
            if (criteria.getProjectoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProjectoId(), root -> root.join(Ficha_.projecto, JoinType.LEFT).get(Projecto_.id))
                    );
            }
        }
        return specification;
    }
}
