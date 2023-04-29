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
    @Mapping(target = "id", source = "id")
    ServicesDTO toDtoServicesId(Services services);
}
