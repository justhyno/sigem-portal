package gov.cmcm.service;

import gov.cmcm.domain.*; // for static metamodels
import gov.cmcm.domain.SpatialUnit;
import gov.cmcm.repository.SpatialUnitRepository;
import gov.cmcm.service.criteria.SpatialUnitCriteria;
import gov.cmcm.service.dto.SpatialUnitDTO;
import gov.cmcm.service.mapper.SpatialUnitMapper;
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
 * Service for executing complex queries for {@link SpatialUnit} entities in the database.
 * The main input is a {@link SpatialUnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpatialUnitDTO} or a {@link Page} of {@link SpatialUnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpatialUnitQueryService extends QueryService<SpatialUnit> {

    private final Logger log = LoggerFactory.getLogger(SpatialUnitQueryService.class);

    private final SpatialUnitRepository spatialUnitRepository;

    private final SpatialUnitMapper spatialUnitMapper;

    public SpatialUnitQueryService(SpatialUnitRepository spatialUnitRepository, SpatialUnitMapper spatialUnitMapper) {
        this.spatialUnitRepository = spatialUnitRepository;
        this.spatialUnitMapper = spatialUnitMapper;
    }

    /**
     * Return a {@link List} of {@link SpatialUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpatialUnitDTO> findByCriteria(SpatialUnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpatialUnit> specification = createSpecification(criteria);
        return spatialUnitMapper.toDto(spatialUnitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpatialUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpatialUnitDTO> findByCriteria(SpatialUnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpatialUnit> specification = createSpecification(criteria);
        return spatialUnitRepository.findAll(specification, page).map(spatialUnitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpatialUnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpatialUnit> specification = createSpecification(criteria);
        return spatialUnitRepository.count(specification);
    }

    /**
     * Function to convert {@link SpatialUnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpatialUnit> createSpecification(SpatialUnitCriteria criteria) {
        Specification<SpatialUnit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SpatialUnit_.id));
            }
            if (criteria.getParcela() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParcela(), SpatialUnit_.parcela));
            }
            if (criteria.getFichaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFichaId(), root -> root.join(SpatialUnit_.ficha, JoinType.LEFT).get(Ficha_.id))
                    );
            }
        }
        return specification;
    }
}
