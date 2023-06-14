package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.domain.Location;
import com.altair.eightstar.domain.ParkingAll;
import com.altair.eightstar.service.dto.HotelDTO;
import com.altair.eightstar.service.dto.LocationDTO;
import com.altair.eightstar.service.dto.ParkingAllDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParkingAll} and its DTO {@link ParkingAllDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParkingAllMapper extends EntityMapper<ParkingAllDTO, ParkingAll> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    ParkingAllDTO toDto(ParkingAll s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelDTO toDtoHotelId(Hotel hotel);
}
