package gov.cmcm.service.mapper;

import gov.cmcm.domain.Pontos;
import gov.cmcm.domain.SpatialUnit;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.dto.SpatialUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pontos} and its DTO {@link PontosDTO}.
 */
@Mapper(componentModel = "spring")
public interface PontosMapper extends EntityMapper<PontosDTO, Pontos> {
    @Mapping(target = "spatialUnit", source = "spatialUnit", qualifiedByName = "spatialUnitId")
    PontosDTO toDto(Pontos s);

    @Named("spatialUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SpatialUnitDTO toDtoSpatialUnitId(SpatialUnit spatialUnit);
}
