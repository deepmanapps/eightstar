package com.altair.eightstar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingAllDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingAllDTO.class);
        ParkingAllDTO parkingAllDTO1 = new ParkingAllDTO();
        parkingAllDTO1.setId(1L);
        ParkingAllDTO parkingAllDTO2 = new ParkingAllDTO();
        assertThat(parkingAllDTO1).isNotEqualTo(parkingAllDTO2);
        parkingAllDTO2.setId(parkingAllDTO1.getId());
        assertThat(parkingAllDTO1).isEqualTo(parkingAllDTO2);
        parkingAllDTO2.setId(2L);
        assertThat(parkingAllDTO1).isNotEqualTo(parkingAllDTO2);
        parkingAllDTO1.setId(null);
        assertThat(parkingAllDTO1).isNotEqualTo(parkingAllDTO2);
    }
}
