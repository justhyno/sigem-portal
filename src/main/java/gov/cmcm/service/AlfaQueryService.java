package gov.cmcm.service;

import gov.cmcm.domain.*; // for static metamodels
import gov.cmcm.domain.Alfa;
import gov.cmcm.repository.AlfaRepository;
import gov.cmcm.service.criteria.AlfaCriteria;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.mapper.AlfaMapper;
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
 * Service for executing complex queries for {@link Alfa} entities in the database.
 * The main input is a {@link AlfaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlfaDTO} or a {@link Page} of {@link AlfaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlfaQueryService extends QueryService<Alfa> {

    private final Logger log = LoggerFactory.getLogger(AlfaQueryService.class);

    private final AlfaRepository alfaRepository;

    private final AlfaMapper alfaMapper;

    public AlfaQueryService(AlfaRepository alfaRepository, AlfaMapper alfaMapper) {
        this.alfaRepository = alfaRepository;
        this.alfaMapper = alfaMapper;
    }

    /**
     * Return a {@link List} of {@link AlfaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlfaDTO> findByCriteria(AlfaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Alfa> specification = createSpecification(criteria);
        return alfaMapper.toDto(alfaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlfaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlfaDTO> findByCriteria(AlfaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Alfa> specification = createSpecification(criteria);
        return alfaRepository.findAll(specification, page).map(alfaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlfaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Alfa> specification = createSpecification(criteria);
        return alfaRepository.count(specification);
    }

    /**
     * Function to convert {@link AlfaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Alfa> createSpecification(AlfaCriteria criteria) {
        Specification<Alfa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Alfa_.id));
            }
            if (criteria.getParcela() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParcela(), Alfa_.parcela));
            }
            if (criteria.getDataLevantamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataLevantamento(), Alfa_.dataLevantamento));
            }
            if (criteria.getFichaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFichaId(), root -> root.join(Alfa_.ficha, JoinType.LEFT).get(Ficha_.id))
                    );
            }
        }
        return specification;
    }
}
