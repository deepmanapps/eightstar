package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.HotelServices} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelServicesDTO implements Serializable {

    private Long id;

    private Boolean active;

    private Boolean forGuest;

    private Float servicePrice;

    private HotelDTO hotel;

    private ServicesDTO services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getForGuest() {
        return forGuest;
    }

    public void setForGuest(Boolean forGuest) {
        this.forGuest = forGuest;
    }

    public Float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public ServicesDTO getServices() {
        return services;
    }

    public void setServices(ServicesDTO services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelServicesDTO)) {
            return false;
        }

        HotelServicesDTO hotelServicesDTO = (HotelServicesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelServicesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelServicesDTO{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", forGuest='" + getForGuest() + "'" +
            ", servicePrice=" + getServicePrice() +
            ", hotel=" + getHotel() +
            ", services=" + getServices() +
            "}";
    }
}
