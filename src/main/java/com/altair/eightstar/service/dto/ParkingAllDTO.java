package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.ParkingAll} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParkingAllDTO implements Serializable {

    private Long id;

    private String name;

    private LocationDTO location;

    private HotelDTO hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingAllDTO)) {
            return false;
        }

        ParkingAllDTO parkingAllDTO = (ParkingAllDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parkingAllDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingAllDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location=" + getLocation() +
            ", hotel=" + getHotel() +
            "}";
    }
}
