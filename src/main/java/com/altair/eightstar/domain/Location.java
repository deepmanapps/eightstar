package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @JsonIgnoreProperties(value = { "location", "checkIns", "parkingAlls", "hotelServices", "deliveryRequestPlaces" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private Hotel hotel;

    @JsonIgnoreProperties(value = { "location", "serviceRequest", "hotel" }, allowSetters = true)
    @OneToOne(mappedBy = "location")
    private ParkingAll parkingAll;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Location longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Location latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        if (this.hotel != null) {
            this.hotel.setLocation(null);
        }
        if (hotel != null) {
            hotel.setLocation(this);
        }
        this.hotel = hotel;
    }

    public Location hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    public ParkingAll getParkingAll() {
        return this.parkingAll;
    }

    public void setParkingAll(ParkingAll parkingAll) {
        if (this.parkingAll != null) {
            this.parkingAll.setLocation(null);
        }
        if (parkingAll != null) {
            parkingAll.setLocation(this);
        }
        this.parkingAll = parkingAll;
    }

    public Location parkingAll(ParkingAll parkingAll) {
        this.setParkingAll(parkingAll);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            "}";
    }
}
