package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckOutMapperTest {

    private CheckOutMapper checkOutMapper;

    @BeforeEach
    public void setUp() {
        checkOutMapper = new CheckOutMapperImpl();
    }
}
