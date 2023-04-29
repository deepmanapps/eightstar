package com.altair.eightstar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.altair.eightstar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingAllTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingAll.class);
        ParkingAll parkingAll1 = new ParkingAll();
        parkingAll1.setId(1L);
        ParkingAll parkingAll2 = new ParkingAll();
        parkingAll2.setId(parkingAll1.getId());
        assertThat(parkingAll1).isEqualTo(parkingAll2);
        parkingAll2.setId(2L);
        assertThat(parkingAll1).isNotEqualTo(parkingAll2);
        parkingAll1.setId(null);
        assertThat(parkingAll1).isNotEqualTo(parkingAll2);
    }
}
