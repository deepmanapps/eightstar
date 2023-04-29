package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryRequestPlaceMapperTest {

    private DeliveryRequestPlaceMapper deliveryRequestPlaceMapper;

    @BeforeEach
    public void setUp() {
        deliveryRequestPlaceMapper = new DeliveryRequestPlaceMapperImpl();
    }
}
