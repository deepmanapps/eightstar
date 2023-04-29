package com.altair.eightstar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelServicesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelServicesDTO.class);
        HotelServicesDTO hotelServicesDTO1 = new HotelServicesDTO();
        hotelServicesDTO1.setId(1L);
        HotelServicesDTO hotelServicesDTO2 = new HotelServicesDTO();
        assertThat(hotelServicesDTO1).isNotEqualTo(hotelServicesDTO2);
        hotelServicesDTO2.setId(hotelServicesDTO1.getId());
        assertThat(hotelServicesDTO1).isEqualTo(hotelServicesDTO2);
        hotelServicesDTO2.setId(2L);
        assertThat(hotelServicesDTO1).isNotEqualTo(hotelServicesDTO2);
        hotelServicesDTO1.setId(null);
        assertThat(hotelServicesDTO1).isNotEqualTo(hotelServicesDTO2);
    }
}
