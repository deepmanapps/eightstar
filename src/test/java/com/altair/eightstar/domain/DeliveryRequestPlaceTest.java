package com.altair.eightstar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryRequestPlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryRequestPlace.class);
        DeliveryRequestPlace deliveryRequestPlace1 = new DeliveryRequestPlace();
        deliveryRequestPlace1.setId(1L);
        DeliveryRequestPlace deliveryRequestPlace2 = new DeliveryRequestPlace();
        deliveryRequestPlace2.setId(deliveryRequestPlace1.getId());
        assertThat(deliveryRequestPlace1).isEqualTo(deliveryRequestPlace2);
        deliveryRequestPlace2.setId(2L);
        assertThat(deliveryRequestPlace1).isNotEqualTo(deliveryRequestPlace2);
        deliveryRequestPlace1.setId(null);
        assertThat(deliveryRequestPlace1).isNotEqualTo(deliveryRequestPlace2);
    }
}
