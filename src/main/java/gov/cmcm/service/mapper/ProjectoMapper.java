package gov.cmcm.service.mapper;

import gov.cmcm.domain.Projecto;
import gov.cmcm.service.dto.ProjectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projecto} and its DTO {@link ProjectoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectoMapper extends EntityMapper<ProjectoDTO, Projecto> {}
