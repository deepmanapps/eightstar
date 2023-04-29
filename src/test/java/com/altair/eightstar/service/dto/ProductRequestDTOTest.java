package com.altair.eightstar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductRequestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductRequestDTO.class);
        ProductRequestDTO productRequestDTO1 = new ProductRequestDTO();
        productRequestDTO1.setId(1L);
        ProductRequestDTO productRequestDTO2 = new ProductRequestDTO();
        assertThat(productRequestDTO1).isNotEqualTo(productRequestDTO2);
        productRequestDTO2.setId(productRequestDTO1.getId());
        assertThat(productRequestDTO1).isEqualTo(productRequestDTO2);
        productRequestDTO2.setId(2L);
        assertThat(productRequestDTO1).isNotEqualTo(productRequestDTO2);
        productRequestDTO1.setId(null);
        assertThat(productRequestDTO1).isNotEqualTo(productRequestDTO2);
    }
}
