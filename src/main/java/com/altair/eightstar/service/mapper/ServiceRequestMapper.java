package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.CheckIn;
import com.altair.eightstar.domain.DeliveryRequestPlace;
import com.altair.eightstar.domain.ParkingAll;
import com.altair.eightstar.domain.ServiceRequest;
import com.altair.eightstar.domain.Services;
import com.altair.eightstar.service.dto.CheckInDTO;
import com.altair.eightstar.service.dto.DeliveryRequestPlaceDTO;
import com.altair.eightstar.service.dto.ParkingAllDTO;
import com.altair.eightstar.service.dto.ServiceRequestDTO;
import com.altair.eightstar.service.dto.ServicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceRequestMapper extends EntityMapper<ServiceRequestDTO, ServiceRequest> {
    @Mapping(target = "parkingAll", source = "parkingAll", qualifiedByName = "parkingAllId")
    @Mapping(target = "deliveryRequestPlace", source = "deliveryRequestPlace", qualifiedByName = "deliveryRequestPlaceId")
    @Mapping(target = "services", source = "services", qualifiedByName = "servicesId")
    @Mapping(target = "checkIn", source = "checkIn", qualifiedByName = "checkInId")
    ServiceRequestDTO toDto(ServiceRequest s);

    @Named("parkingAllId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParkingAllDTO toDtoParkingAllId(ParkingAll parkingAll);

    @Named("deliveryRequestPlaceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeliveryRequestPlaceDTO toDtoDeliveryRequestPlaceId(DeliveryRequestPlace deliveryRequestPlace);

    @Named("servicesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    ServicesDTO toDtoServicesId(Services services);

    @Named("checkInId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CheckInDTO toDtoCheckInId(CheckIn checkIn);
}
