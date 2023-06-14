package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.DeliveryRequestPlace;
import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import com.altair.eightstar.service.dto.HotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryRequestPlace} and its DTO {@link DeliveryRequestPlaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryRequestPlaceMapper extends EntityMapper<DeliveryRequestPlaceDTO, DeliveryRequestPlace> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    DeliveryRequestPlaceDTO toDto(DeliveryRequestPlace s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelDTO toDtoHotelId(Hotel hotel);
}
