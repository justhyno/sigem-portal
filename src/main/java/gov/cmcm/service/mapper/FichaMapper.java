package gov.cmcm.service.mapper;

import gov.cmcm.domain.Ficha;
import gov.cmcm.domain.Projecto;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.dto.ProjectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ficha} and its DTO {@link FichaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FichaMapper extends EntityMapper<FichaDTO, Ficha> {
    @Mapping(target = "projecto", source = "projecto", qualifiedByName = "projectoDescricao")
    FichaDTO toDto(Ficha s);

    @Named("projectoDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ProjectoDTO toDtoProjectoDescricao(Projecto projecto);
}
