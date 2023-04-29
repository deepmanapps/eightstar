package com.altair.eightstar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryRequestPlaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryRequestPlaceDTO.class);
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO1 = new DeliveryRequestPlaceDTO();
        deliveryRequestPlaceDTO1.setId(1L);
        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO2 = new DeliveryRequestPlaceDTO();
        assertThat(deliveryRequestPlaceDTO1).isNotEqualTo(deliveryRequestPlaceDTO2);
        deliveryRequestPlaceDTO2.setId(deliveryRequestPlaceDTO1.getId());
        assertThat(deliveryRequestPlaceDTO1).isEqualTo(deliveryRequestPlaceDTO2);
        deliveryRequestPlaceDTO2.setId(2L);
        assertThat(deliveryRequestPlaceDTO1).isNotEqualTo(deliveryRequestPlaceDTO2);
        deliveryRequestPlaceDTO1.setId(null);
        assertThat(deliveryRequestPlaceDTO1).isNotEqualTo(deliveryRequestPlaceDTO2);
    }
}
