package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.CheckIn;
import com.altair.eightstar.domain.CheckOut;
import com.altair.eightstar.domain.Customer;
import com.altair.eightstar.domain.Hotel;
import com.altair.eightstar.service.dto.CheckInDTO;
import com.altair.eightstar.service.dto.CheckOutDTO;
import com.altair.eightstar.service.dto.CustomerDTO;
import com.altair.eightstar.service.dto.HotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckIn} and its DTO {@link CheckInDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckInMapper extends EntityMapper<CheckInDTO, CheckIn> {
    @Mapping(target = "checkOut", source = "checkOut", qualifiedByName = "checkOutId")
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    CheckInDTO toDto(CheckIn s);

    @Named("checkOutId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CheckOutDTO toDtoCheckOutId(CheckOut checkOut);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelDTO toDtoHotelId(Hotel hotel);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    CustomerDTO toDtoCustomerId(Customer customer);
}
