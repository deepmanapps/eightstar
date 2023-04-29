package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.DeliveryRequestPlace} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryRequestPlaceDTO implements Serializable {

    private Long id;

    private String name;

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
        if (!(o instanceof DeliveryRequestPlaceDTO)) {
            return false;
        }

        DeliveryRequestPlaceDTO deliveryRequestPlaceDTO = (DeliveryRequestPlaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deliveryRequestPlaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryRequestPlaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hotel=" + getHotel() +
            "}";
    }
}
