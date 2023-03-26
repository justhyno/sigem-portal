package gov.cmcm.service.mapper;

import gov.cmcm.domain.Ficha;
import gov.cmcm.domain.SpatialUnit;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.dto.SpatialUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpatialUnit} and its DTO {@link SpatialUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpatialUnitMapper extends EntityMapper<SpatialUnitDTO, SpatialUnit> {
    @Mapping(target = "ficha", source = "ficha", qualifiedByName = "fichaProcesso")
    SpatialUnitDTO toDto(SpatialUnit s);

    @Named("fichaProcesso")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "processo", source = "processo")
    FichaDTO toDtoFichaProcesso(Ficha ficha);
}
