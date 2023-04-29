package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckInMapperTest {

    private CheckInMapper checkInMapper;

    @BeforeEach
    public void setUp() {
        checkInMapper = new CheckInMapperImpl();
    }
}
