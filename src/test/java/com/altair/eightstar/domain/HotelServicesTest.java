package com.altair.eightstar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelServicesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelServices.class);
        HotelServices hotelServices1 = new HotelServices();
        hotelServices1.setId(1L);
        HotelServices hotelServices2 = new HotelServices();
        hotelServices2.setId(hotelServices1.getId());
        assertThat(hotelServices1).isEqualTo(hotelServices2);
        hotelServices2.setId(2L);
        assertThat(hotelServices1).isNotEqualTo(hotelServices2);
        hotelServices1.setId(null);
        assertThat(hotelServices1).isNotEqualTo(hotelServices2);
    }
}
