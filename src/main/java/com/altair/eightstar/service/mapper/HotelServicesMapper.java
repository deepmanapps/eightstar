package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.domain.HotelServices;
import com.altair.eightstar.domain.Services;
import com.altair.eightstar.service.dto.HotelDTO;
import com.altair.eightstar.service.dto.HotelServicesDTO;
import com.altair.eightstar.service.dto.ServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelServices} and its DTO {@link HotelServicesDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelServicesMapper extends EntityMapper<HotelServicesDTO, HotelServices> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    @Mapping(target = "services", source = "services", qualifiedByName = "servicesId")
    HotelServicesDTO toDto(HotelServices s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelDTO toDtoHotelId(Hotel hotel);

    @Named("servicesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    ServicesDTO toDtoServicesId(Services services);
}
