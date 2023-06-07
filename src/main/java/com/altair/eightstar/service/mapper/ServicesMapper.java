package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.Services;
import com.altair.eightstar.service.dto.ServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Services} and its DTO {@link ServicesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicesMapper extends EntityMapper<ServicesDTO, Services> {
    @Mapping(target = "parentService", source = "parentService", qualifiedByName = "servicesId")
    ServicesDTO toDto(Services s);

    @Named("servicesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "parentService", source = "parentService")
    ServicesDTO toDtoServicesId(Services services);
}
