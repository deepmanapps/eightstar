package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A HotelServices.
 */
@Entity
@Table(name = "hotel_services")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "for_guest")
    private Boolean forGuest;

    @Column(name = "service_price")
    private Float servicePrice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "checkIns", "parkingAlls", "hotelServices", "deliveryRequestPlaces" }, allowSetters = true)
    private Hotel hotel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "childServices", "hotelServices", "parentService", "serviceRequests" }, allowSetters = true)
    private Services services;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HotelServices id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return this.active;
    }

    public HotelServices active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getForGuest() {
        return this.forGuest;
    }

    public HotelServices forGuest(Boolean forGuest) {
        this.setForGuest(forGuest);
        return this;
    }

    public void setForGuest(Boolean forGuest) {
        this.forGuest = forGuest;
    }

    public Float getServicePrice() {
        return this.servicePrice;
    }

    public HotelServices servicePrice(Float servicePrice) {
        this.setServicePrice(servicePrice);
        return this;
    }

    public void setServicePrice(Float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public HotelServices hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    public Services getServices() {
        return this.services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public HotelServices services(Services services) {
        this.setServices(services);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelServices)) {
            return false;
        }
        return id != null && id.equals(((HotelServices) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelServices{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", forGuest='" + getForGuest() + "'" +
            ", servicePrice=" + getServicePrice() +
            "}";
    }
}
