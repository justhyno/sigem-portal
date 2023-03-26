package gov.cmcm.service.impl;

import gov.cmcm.domain.Ficha;
import gov.cmcm.repository.FichaRepository;
import gov.cmcm.service.FichaService;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.mapper.FichaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ficha}.
 */
@Service
@Transactional
public class FichaServiceImpl implements FichaService {

    private final Logger log = LoggerFactory.getLogger(FichaServiceImpl.class);

    private final FichaRepository fichaRepository;

    private final FichaMapper fichaMapper;

    public FichaServiceImpl(FichaRepository fichaRepository, FichaMapper fichaMapper) {
        this.fichaRepository = fichaRepository;
        this.fichaMapper = fichaMapper;
    }

    @Override
    public FichaDTO save(FichaDTO fichaDTO) {
        log.debug("Request to save Ficha : {}", fichaDTO);
        Ficha ficha = fichaMapper.toEntity(fichaDTO);
        ficha = fichaRepository.save(ficha);
        return fichaMapper.toDto(ficha);
    }

    @Override
    public FichaDTO update(FichaDTO fichaDTO) {
        log.debug("Request to update Ficha : {}", fichaDTO);
        Ficha ficha = fichaMapper.toEntity(fichaDTO);
        ficha = fichaRepository.save(ficha);
        return fichaMapper.toDto(ficha);
    }

    @Override
    public Optional<FichaDTO> partialUpdate(FichaDTO fichaDTO) {
        log.debug("Request to partially update Ficha : {}", fichaDTO);

        return fichaRepository
            .findById(fichaDTO.getId())
            .map(existingFicha -> {
                fichaMapper.partialUpdate(existingFicha, fichaDTO);

                return existingFicha;
            })
            .map(fichaRepository::save)
            .map(fichaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FichaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fichas");
        return fichaRepository.findAll(pageable).map(fichaMapper::toDto);
    }

    public Page<FichaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fichaRepository.findAllWithEagerRelationships(pageable).map(fichaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FichaDTO> findOne(Long id) {
        log.debug("Request to get Ficha : {}", id);
        return fichaRepository.findOneWithEagerRelationships(id).map(fichaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ficha : {}", id);
        fichaRepository.deleteById(id);
    }
}
