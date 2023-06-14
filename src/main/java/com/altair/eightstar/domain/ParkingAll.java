package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ParkingAll.
 */
@Entity
@Table(name = "parking_all")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParkingAll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnoreProperties(value = { "hotel", "parkingAll" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "parkingAll", "deliveryRequestPlace", "productRequests", "services", "checkIn" }, allowSetters = true)
    @OneToOne(mappedBy = "parkingAll")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "location", "user", "checkIns", "parkingAlls", "hotelServices", "deliveryRequestPlaces" },
        allowSetters = true
    )
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ParkingAll id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ParkingAll name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ParkingAll location(Location location) {
        this.setLocation(location);
        return this;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        if (this.serviceRequest != null) {
            this.serviceRequest.setParkingAll(null);
        }
        if (serviceRequest != null) {
            serviceRequest.setParkingAll(this);
        }
        this.serviceRequest = serviceRequest;
    }

    public ParkingAll serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public ParkingAll hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingAll)) {
            return false;
        }
        return id != null && id.equals(((ParkingAll) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingAll{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
