package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingAllMapperTest {

    private ParkingAllMapper parkingAllMapper;

    @BeforeEach
    public void setUp() {
        parkingAllMapper = new ParkingAllMapperImpl();
    }
}
