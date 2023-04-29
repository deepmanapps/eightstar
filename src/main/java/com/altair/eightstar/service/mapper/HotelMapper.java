package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.domain.Location;
import com.altair.eightstar.service.dto.HotelDTO;
import com.altair.eightstar.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hotel} and its DTO {@link HotelDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelMapper extends EntityMapper<HotelDTO, Hotel> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    HotelDTO toDto(Hotel s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
