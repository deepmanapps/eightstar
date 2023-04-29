package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.ProductRequest;
import com.altair.eightstar.domain.ServiceRequest;
import com.altair.eightstar.service.dto.ProductRequestDTO;
import com.altair.eightstar.service.dto.ServiceRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductRequest} and its DTO {@link ProductRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductRequestMapper extends EntityMapper<ProductRequestDTO, ProductRequest> {
    @Mapping(target = "serviceRequest", source = "serviceRequest", qualifiedByName = "serviceRequestId")
    ProductRequestDTO toDto(ProductRequest s);

    @Named("serviceRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceRequestDTO toDtoServiceRequestId(ServiceRequest serviceRequest);
}
