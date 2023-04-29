package com.altair.eightstar.service.mapper;

import com.altair.eightstar.domain.CheckOut;
import com.altair.eightstar.service.dto.CheckOutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckOut} and its DTO {@link CheckOutDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckOutMapper extends EntityMapper<CheckOutDTO, CheckOut> {}
