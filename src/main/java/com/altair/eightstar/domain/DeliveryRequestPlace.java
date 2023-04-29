package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A DeliveryRequestPlace.
 */
@Entity
@Table(name = "delivery_request_place")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryRequestPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnoreProperties(value = { "parkingAll", "deliveryRequestPlace", "productRequests", "services", "checkIn" }, allowSetters = true)
    @OneToOne(mappedBy = "deliveryRequestPlace")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "checkIns", "parkingAlls", "hotelServices", "deliveryRequestPlaces" }, allowSetters = true)
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeliveryRequestPlace id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DeliveryRequestPlace name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        if (this.serviceRequest != null) {
            this.serviceRequest.setDeliveryRequestPlace(null);
        }
        if (serviceRequest != null) {
            serviceRequest.setDeliveryRequestPlace(this);
        }
        this.serviceRequest = serviceRequest;
    }

    public DeliveryRequestPlace serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public DeliveryRequestPlace hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryRequestPlace)) {
            return false;
        }
        return id != null && id.equals(((DeliveryRequestPlace) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryRequestPlace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
