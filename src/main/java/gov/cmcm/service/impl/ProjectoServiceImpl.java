package gov.cmcm.service.impl;

import gov.cmcm.domain.Projecto;
import gov.cmcm.repository.ProjectoRepository;
import gov.cmcm.service.ProjectoService;
import gov.cmcm.service.TituloService;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.service.mapper.ProjectoMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Projecto}.
 */
@Service
@Transactional
public class ProjectoServiceImpl implements ProjectoService {

    private final Logger log = LoggerFactory.getLogger(ProjectoServiceImpl.class);

    private final ProjectoRepository projectoRepository;

    private final ProjectoMapper projectoMapper;

    private final TituloService tituloService;

    public ProjectoServiceImpl(ProjectoRepository projectoRepository, ProjectoMapper projectoMapper, TituloService tituloService) {
        this.projectoRepository = projectoRepository;
        this.projectoMapper = projectoMapper;
        this.tituloService = tituloService;
    }

    @Override
    public ProjectoDTO save(ProjectoDTO projectoDTO) {
        log.debug("Request to save Projecto : {}", projectoDTO);
        Projecto projecto = projectoMapper.toEntity(projectoDTO);
        projecto = projectoRepository.save(projecto);
        return projectoMapper.toDto(projecto);
    }

    @Override
    public ProjectoDTO update(ProjectoDTO projectoDTO) {
        log.debug("Request to update Projecto : {}", projectoDTO);
        Projecto projecto = projectoMapper.toEntity(projectoDTO);
        projecto = projectoRepository.save(projecto);
        return projectoMapper.toDto(projecto);
    }

    @Override
    public Optional<ProjectoDTO> partialUpdate(ProjectoDTO projectoDTO) {
        log.debug("Request to partially update Projecto : {}", projectoDTO);

        return projectoRepository
            .findById(projectoDTO.getId())
            .map(existingProjecto -> {
                projectoMapper.partialUpdate(existingProjecto, projectoDTO);

                return existingProjecto;
            })
            .map(projectoRepository::save)
            .map(projectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projectos");
        return projectoRepository.findAll(pageable).map(projectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectoDTO> findOne(Long id) {
        log.debug("Request to get Projecto : {}", id);
        return projectoRepository.findById(id).map(projectoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projecto : {}", id);
        projectoRepository.deleteById(id);
    }

    @Override
    public List<ProjectoDTO> findAllProjects() {
        // TODO Auto-generated method stub

        return projectoMapper.toDto(projectoRepository.findAll());
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findAllProjects'");
    }

    @Override
    public ProjectoDTO findByName(String name) {
        // TODO Auto-generated method stub
        return projectoMapper.toDto(projectoRepository.findByNome(name));
    }

    @Override
    public Integer generateTitulo(Long id) {
        // TODO Auto-generated method stub
        tituloService.fetchTitulo(id);
        return 0;
    }
}
