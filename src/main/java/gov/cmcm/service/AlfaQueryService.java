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
            if (criteria.getTipoTitular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoTitular(), Alfa_.tipoTitular));
            }
            if (criteria.getNomeTitular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeTitular(), Alfa_.nomeTitular));
            }
            if (criteria.getEstadoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstadoSocial(), Alfa_.estadoSocial));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataNascimento(), Alfa_.dataNascimento));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexo(), Alfa_.sexo));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumento(), Alfa_.documento));
            }
            if (criteria.getNumeroDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroDocumento(), Alfa_.numeroDocumento));
            }
            if (criteria.getDatEmissao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatEmissao(), Alfa_.datEmissao));
            }
            if (criteria.getLocalEmissao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalEmissao(), Alfa_.localEmissao));
            }
            if (criteria.getContactoPrincipal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactoPrincipal(), Alfa_.contactoPrincipal));
            }
            if (criteria.getContactoAlternativo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactoAlternativo(), Alfa_.contactoAlternativo));
            }
            if (criteria.getEstadoCivil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstadoCivil(), Alfa_.estadoCivil));
            }
            if (criteria.getNomeConjugue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeConjugue(), Alfa_.nomeConjugue));
            }
            if (criteria.getDistritoMunicipal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistritoMunicipal(), Alfa_.distritoMunicipal));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), Alfa_.bairro));
            }
            if (criteria.getQuatreirao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuatreirao(), Alfa_.quatreirao));
            }
            if (criteria.getTalhao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTalhao(), Alfa_.talhao));
            }
            if (criteria.getCelula() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelula(), Alfa_.celula));
            }
            if (criteria.getBloco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBloco(), Alfa_.bloco));
            }
            if (criteria.getAvenida() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAvenida(), Alfa_.avenida));
            }
            if (criteria.getNumeroPolicia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroPolicia(), Alfa_.numeroPolicia));
            }
            if (criteria.getUsoActual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsoActual(), Alfa_.usoActual));
            }
            if (criteria.getFormaUso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormaUso(), Alfa_.formaUso));
            }
            if (criteria.getFormaObtencao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormaObtencao(), Alfa_.formaObtencao));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), Alfa_.tipo));
            }
            if (criteria.getAnoOcupacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnoOcupacao(), Alfa_.anoOcupacao));
            }
            if (criteria.getTipoAcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoAcesso(), Alfa_.tipoAcesso));
            }
            if (criteria.getConflito() != null) {
                specification = specification.and(buildSpecification(criteria.getConflito(), Alfa_.conflito));
            }
            if (criteria.getDetalhesConflito() != null) {
                specification = specification.and(buildSpecification(criteria.getDetalhesConflito(), Alfa_.detalhesConflito));
            }
            if (criteria.getConstrucaoPrecaria() != null) {
                specification = specification.and(buildSpecification(criteria.getConstrucaoPrecaria(), Alfa_.construcaoPrecaria));
            }
            if (criteria.getPisosAcimaSoleira() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPisosAcimaSoleira(), Alfa_.pisosAcimaSoleira));
            }
            if (criteria.getPisosAbaixoSoleira() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPisosAbaixoSoleira(), Alfa_.pisosAbaixoSoleira));
            }
            if (criteria.getMaterialConstrucaoBarrote() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMaterialConstrucaoBarrote(), Alfa_.materialConstrucaoBarrote));
            }
            if (criteria.getMaterialConstrucaoIBR() != null) {
                specification = specification.and(buildSpecification(criteria.getMaterialConstrucaoIBR(), Alfa_.materialConstrucaoIBR));
            }
            if (criteria.getMaterialConstrucaoPranchas() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMaterialConstrucaoPranchas(), Alfa_.materialConstrucaoPranchas));
            }
            if (criteria.getMaterialConstrucaoPau() != null) {
                specification = specification.and(buildSpecification(criteria.getMaterialConstrucaoPau(), Alfa_.materialConstrucaoPau));
            }
            if (criteria.getMaterialConstrucaoCanico() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMaterialConstrucaoCanico(), Alfa_.materialConstrucaoCanico));
            }
            if (criteria.getMaterialConstrucaoCimento() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMaterialConstrucaoCimento(), Alfa_.materialConstrucaoCimento));
            }
            if (criteria.getOcupacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOcupacao(), Alfa_.ocupacao));
            }
            if (criteria.getTipoContrucao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoContrucao(), Alfa_.TipoContrucao));
            }
            if (criteria.getDetalhesTipoContrucao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDetalhesTipoContrucao(), Alfa_.detalhesTipoContrucao));
            }
            if (criteria.getInfraestruturaExistente() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInfraestruturaExistente(), Alfa_.infraestruturaExistente));
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
