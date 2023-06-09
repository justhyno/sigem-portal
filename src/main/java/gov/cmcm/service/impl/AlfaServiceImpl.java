package gov.cmcm.service.impl;

import gov.cmcm.domain.Alfa;
import gov.cmcm.repository.AlfaRepository;
import gov.cmcm.service.AlfaService;
import gov.cmcm.service.FichaService;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.service.mapper.AlfaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Alfa}.
 */
@Service
@Transactional
public class AlfaServiceImpl implements AlfaService {

    private final Logger log = LoggerFactory.getLogger(AlfaServiceImpl.class);

    private final AlfaRepository alfaRepository;

    private final AlfaMapper alfaMapper;

    private final FichaService fichaService;

    public AlfaServiceImpl(AlfaRepository alfaRepository, AlfaMapper alfaMapper, FichaService fichaService) {
        this.alfaRepository = alfaRepository;
        this.alfaMapper = alfaMapper;
        this.fichaService = fichaService;
    }

    @Override
    public AlfaDTO save(AlfaDTO alfaDTO) {
        log.debug("Request to save Alfa : {}", alfaDTO);
        // delete if exist old
        deleteByFicha(alfaDTO);

        Alfa alfa = alfaMapper.toEntity(alfaDTO);
        alfa = alfaRepository.save(alfa);
        return alfaMapper.toDto(alfa);
    }

    @Modifying
    public void deleteByFicha(AlfaDTO alfa) {
        Long ficha = alfa.getFicha().getId();

        alfaRepository.deleteByFicha(ficha);
    }

    @Override
    public AlfaDTO update(AlfaDTO alfaDTO) {
        log.debug("Request to update Alfa : {}", alfaDTO);
        Alfa alfa = alfaMapper.toEntity(alfaDTO);
        alfa = alfaRepository.save(alfa);
        return alfaMapper.toDto(alfa);
    }

    @Override
    public Optional<AlfaDTO> partialUpdate(AlfaDTO alfaDTO) {
        log.debug("Request to partially update Alfa : {}", alfaDTO);

        return alfaRepository
            .findById(alfaDTO.getId())
            .map(existingAlfa -> {
                alfaMapper.partialUpdate(existingAlfa, alfaDTO);

                return existingAlfa;
            })
            .map(alfaRepository::save)
            .map(alfaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlfaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alfas");
        return alfaRepository.findAll(pageable).map(alfaMapper::toDto);
    }

    public Page<AlfaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return alfaRepository.findAllWithEagerRelationships(pageable).map(alfaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlfaDTO> findOne(Long id) {
        log.debug("Request to get Alfa : {}", id);
        return alfaRepository.findOneWithEagerRelationships(id).map(alfaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alfa : {}", id);
        alfaRepository.deleteById(id);
    }

    @Override
    public AlfaDTO save(AlfaDTO alfaDTO, ProjectoDTO projectoDTO) {
        // TODO Auto-generated method stub
        FichaDTO ficha = fichaService.findByProjectAndParcel(projectoDTO.getId(), alfaDTO.getParcela());
        alfaDTO.setFicha(ficha);
        return this.save(alfaDTO);
    }

    @Override
    public Optional<AlfaDTO> findByFicha(long fichaId) {
        Alfa alfa = alfaRepository.findByFichaId(fichaId);
        if (alfa != null) {
            return Optional.of(alfaMapper.toDto(alfa));
        } else {
            return Optional.empty();
        }
    }
}
