package com.altair.eightstar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductRequest.class);
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setId(1L);
        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setId(productRequest1.getId());
        assertThat(productRequest1).isEqualTo(productRequest2);
        productRequest2.setId(2L);
        assertThat(productRequest1).isNotEqualTo(productRequest2);
        productRequest1.setId(null);
        assertThat(productRequest1).isNotEqualTo(productRequest2);
    }
}
