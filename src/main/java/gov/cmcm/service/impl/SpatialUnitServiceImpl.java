package gov.cmcm.service.impl;

import gov.cmcm.domain.SpatialUnit;
import gov.cmcm.repository.SpatialUnitRepository;
import gov.cmcm.service.SpatialUnitService;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.dto.SpatialUnitDTO;
import gov.cmcm.service.mapper.SpatialUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpatialUnit}.
 */
@Service
@Transactional
public class SpatialUnitServiceImpl implements SpatialUnitService {

    private final Logger log = LoggerFactory.getLogger(SpatialUnitServiceImpl.class);

    private final SpatialUnitRepository spatialUnitRepository;

    private final SpatialUnitMapper spatialUnitMapper;

    private final FichaServiceImpl fichaServiceImpl;

    @Override
    public SpatialUnitDTO save(SpatialUnitDTO spatialUnitDTO) {
        log.debug("Request to save SpatialUnit : {}", spatialUnitDTO);
        SpatialUnit spatialUnit = spatialUnitMapper.toEntity(spatialUnitDTO);
        spatialUnit = spatialUnitRepository.save(spatialUnit);
        return spatialUnitMapper.toDto(spatialUnit);
    }

    public SpatialUnitServiceImpl(
        SpatialUnitRepository spatialUnitRepository,
        SpatialUnitMapper spatialUnitMapper,
        FichaServiceImpl fichaServiceImpl
    ) {
        this.spatialUnitRepository = spatialUnitRepository;
        this.spatialUnitMapper = spatialUnitMapper;
        this.fichaServiceImpl = fichaServiceImpl;
    }

    @Override
    public SpatialUnitDTO update(SpatialUnitDTO spatialUnitDTO) {
        log.debug("Request to update SpatialUnit : {}", spatialUnitDTO);
        SpatialUnit spatialUnit = spatialUnitMapper.toEntity(spatialUnitDTO);
        spatialUnit = spatialUnitRepository.save(spatialUnit);
        return spatialUnitMapper.toDto(spatialUnit);
    }

    @Override
    public Optional<SpatialUnitDTO> partialUpdate(SpatialUnitDTO spatialUnitDTO) {
        log.debug("Request to partially update SpatialUnit : {}", spatialUnitDTO);

        return spatialUnitRepository
            .findById(spatialUnitDTO.getId())
            .map(existingSpatialUnit -> {
                spatialUnitMapper.partialUpdate(existingSpatialUnit, spatialUnitDTO);

                return existingSpatialUnit;
            })
            .map(spatialUnitRepository::save)
            .map(spatialUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpatialUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpatialUnits");
        return spatialUnitRepository.findAll(pageable).map(spatialUnitMapper::toDto);
    }

    public Page<SpatialUnitDTO> findAllWithEagerRelationships(Pageable pageable) {
        return spatialUnitRepository.findAllWithEagerRelationships(pageable).map(spatialUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SpatialUnitDTO> findOne(Long id) {
        log.debug("Request to get SpatialUnit : {}", id);
        return spatialUnitRepository.findOneWithEagerRelationships(id).map(spatialUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpatialUnit : {}", id);
        spatialUnitRepository.deleteById(id);
    }

    @Override
    public SpatialUnitDTO getByProjectoAndParcela(Long projecto, String parcela) {
        // TODO Auto-generated method stub'
        FichaDTO ficha = fichaServiceImpl.findByProjectAndParcel(projecto, parcela);
        Optional<SpatialUnit> spu = spatialUnitRepository.getByProjectoByFicha(ficha.getId());

        if (!spu.isEmpty()) return spatialUnitMapper.toDto(spu.get()); else {
            SpatialUnitDTO spuDto = new SpatialUnitDTO();
            spuDto.setFicha(ficha);
            spuDto.setParcela(parcela);
            return this.save(spuDto);
        }
    }

    @Override
    public Optional<SpatialUnitDTO> findByFichaId(Long ficha) {
        // TODO Auto-generated method stub
        Optional<SpatialUnit> spatialUnit = spatialUnitRepository.findByFichaId(ficha);

        if (spatialUnit.isPresent()) {
            return Optional.of(spatialUnitMapper.toDto(spatialUnit.get()));
        } else return Optional.empty();
    }
}
