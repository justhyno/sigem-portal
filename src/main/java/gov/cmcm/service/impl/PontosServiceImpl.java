package gov.cmcm.service.impl;

import gov.cmcm.domain.Pontos;
import gov.cmcm.repository.PontosRepository;
import gov.cmcm.service.PontosService;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.mapper.PontosMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pontos}.
 */
@Service
@Transactional
public class PontosServiceImpl implements PontosService {

    private final Logger log = LoggerFactory.getLogger(PontosServiceImpl.class);

    private final PontosRepository pontosRepository;

    private final PontosMapper pontosMapper;

    public PontosServiceImpl(PontosRepository pontosRepository, PontosMapper pontosMapper) {
        this.pontosRepository = pontosRepository;
        this.pontosMapper = pontosMapper;
    }

    @Override
    public PontosDTO save(PontosDTO pontosDTO) {
        log.debug("Request to save Pontos : {}", pontosDTO);
        Pontos pontos = pontosMapper.toEntity(pontosDTO);
        pontos = pontosRepository.save(pontos);
        return pontosMapper.toDto(pontos);
    }

    @Override
    public PontosDTO update(PontosDTO pontosDTO) {
        log.debug("Request to update Pontos : {}", pontosDTO);
        Pontos pontos = pontosMapper.toEntity(pontosDTO);
        pontos = pontosRepository.save(pontos);
        return pontosMapper.toDto(pontos);
    }

    @Override
    public Optional<PontosDTO> partialUpdate(PontosDTO pontosDTO) {
        log.debug("Request to partially update Pontos : {}", pontosDTO);

        return pontosRepository
            .findById(pontosDTO.getId())
            .map(existingPontos -> {
                pontosMapper.partialUpdate(existingPontos, pontosDTO);

                return existingPontos;
            })
            .map(pontosRepository::save)
            .map(pontosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PontosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pontos");
        return pontosRepository.findAll(pageable).map(pontosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PontosDTO> findOne(Long id) {
        log.debug("Request to get Pontos : {}", id);
        return pontosRepository.findById(id).map(pontosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pontos : {}", id);
        pontosRepository.deleteById(id);
    }
}
