package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceRequestMapperTest {

    private ServiceRequestMapper serviceRequestMapper;

    @BeforeEach
    public void setUp() {
        serviceRequestMapper = new ServiceRequestMapperImpl();
    }
}
