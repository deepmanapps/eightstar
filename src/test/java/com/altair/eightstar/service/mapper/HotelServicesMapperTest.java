package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelServicesMapperTest {

    private HotelServicesMapper hotelServicesMapper;

    @BeforeEach
    public void setUp() {
        hotelServicesMapper = new HotelServicesMapperImpl();
    }
}
