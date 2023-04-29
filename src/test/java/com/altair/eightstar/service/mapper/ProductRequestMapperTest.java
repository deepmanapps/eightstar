package com.altair.eightstar.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductRequestMapperTest {

    private ProductRequestMapper productRequestMapper;

    @BeforeEach
    public void setUp() {
        productRequestMapper = new ProductRequestMapperImpl();
    }
}
