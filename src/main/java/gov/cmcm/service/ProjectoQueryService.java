package gov.cmcm.service;

import gov.cmcm.domain.*; // for static metamodels
import gov.cmcm.domain.Projecto;
import gov.cmcm.repository.ProjectoRepository;
import gov.cmcm.service.criteria.ProjectoCriteria;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.service.mapper.ProjectoMapper;
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
 * Service for executing complex queries for {@link Projecto} entities in the database.
 * The main input is a {@link ProjectoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectoDTO} or a {@link Page} of {@link ProjectoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectoQueryService extends QueryService<Projecto> {

    private final Logger log = LoggerFactory.getLogger(ProjectoQueryService.class);

    private final ProjectoRepository projectoRepository;

    private final ProjectoMapper projectoMapper;

    public ProjectoQueryService(ProjectoRepository projectoRepository, ProjectoMapper projectoMapper) {
        this.projectoRepository = projectoRepository;
        this.projectoMapper = projectoMapper;
    }

    /**
     * Return a {@link List} of {@link ProjectoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectoDTO> findByCriteria(ProjectoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Projecto> specification = createSpecification(criteria);
        return projectoMapper.toDto(projectoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectoDTO> findByCriteria(ProjectoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Projecto> specification = createSpecification(criteria);
        return projectoRepository.findAll(specification, page).map(projectoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Projecto> specification = createSpecification(criteria);
        return projectoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Projecto> createSpecification(ProjectoCriteria criteria) {
        Specification<Projecto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Projecto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Projecto_.nome));
            }
            if (criteria.getZona() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZona(), Projecto_.zona));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Projecto_.descricao));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodigo(), Projecto_.codigo));
            }
        }
        return specification;
    }
}
