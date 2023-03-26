package gov.cmcm.service.mapper;

import gov.cmcm.domain.Alfa;
import gov.cmcm.domain.Ficha;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.FichaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alfa} and its DTO {@link AlfaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlfaMapper extends EntityMapper<AlfaDTO, Alfa> {
    @Mapping(target = "ficha", source = "ficha", qualifiedByName = "fichaProcesso")
    AlfaDTO toDto(Alfa s);

    @Named("fichaProcesso")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "processo", source = "processo")
    FichaDTO toDtoFichaProcesso(Ficha ficha);
}
