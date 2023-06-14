package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * The Entity entity.\n@author A true hipster
 */
@Entity
@Table(name = "hotel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Minimum d'info sur l'hotel
     */
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "stars_number")
    private Integer starsNumber;

    @Column(name = "emergency_number")
    private Double emergencyNumber;

    @JsonIgnoreProperties(value = { "hotel", "parkingAll" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "checkOut", "serviceRequests", "hotel", "customer" }, allowSetters = true)
    private Set<CheckIn> checkIns = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "location", "serviceRequest", "hotel" }, allowSetters = true)
    private Set<ParkingAll> parkingAlls = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "hotel", "services" }, allowSetters = true)
    private Set<HotelServices> hotelServices = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @JsonIgnoreProperties(value = { "serviceRequest", "hotel" }, allowSetters = true)
    private Set<DeliveryRequestPlace> deliveryRequestPlaces = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return this.hotelId;
    }

    public Hotel hotelId(Long hotelId) {
        this.setHotelId(hotelId);
        return this;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return this.name;
    }

    public Hotel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Hotel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Hotel adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getStarsNumber() {
        return this.starsNumber;
    }

    public Hotel starsNumber(Integer starsNumber) {
        this.setStarsNumber(starsNumber);
        return this;
    }

    public void setStarsNumber(Integer starsNumber) {
        this.starsNumber = starsNumber;
    }

    public Double getEmergencyNumber() {
        return this.emergencyNumber;
    }

    public Hotel emergencyNumber(Double emergencyNumber) {
        this.setEmergencyNumber(emergencyNumber);
        return this;
    }

    public void setEmergencyNumber(Double emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Hotel location(Location location) {
        this.setLocation(location);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<CheckIn> getCheckIns() {
        return this.checkIns;
    }

    public void setCheckIns(Set<CheckIn> checkIns) {
        if (this.checkIns != null) {
            this.checkIns.forEach(i -> i.setHotel(null));
        }
        if (checkIns != null) {
            checkIns.forEach(i -> i.setHotel(this));
        }
        this.checkIns = checkIns;
    }

    public Hotel checkIns(Set<CheckIn> checkIns) {
        this.setCheckIns(checkIns);
        return this;
    }

    public Hotel addCheckIn(CheckIn checkIn) {
        this.checkIns.add(checkIn);
        checkIn.setHotel(this);
        return this;
    }

    public Hotel removeCheckIn(CheckIn checkIn) {
        this.checkIns.remove(checkIn);
        checkIn.setHotel(null);
        return this;
    }

    public Set<ParkingAll> getParkingAlls() {
        return this.parkingAlls;
    }

    public void setParkingAlls(Set<ParkingAll> parkingAlls) {
        if (this.parkingAlls != null) {
            this.parkingAlls.forEach(i -> i.setHotel(null));
        }
        if (parkingAlls != null) {
            parkingAlls.forEach(i -> i.setHotel(this));
        }
        this.parkingAlls = parkingAlls;
    }

    public Hotel parkingAlls(Set<ParkingAll> parkingAlls) {
        this.setParkingAlls(parkingAlls);
        return this;
    }

    public Hotel addParkingAll(ParkingAll parkingAll) {
        this.parkingAlls.add(parkingAll);
        parkingAll.setHotel(this);
        return this;
    }

    public Hotel removeParkingAll(ParkingAll parkingAll) {
        this.parkingAlls.remove(parkingAll);
        parkingAll.setHotel(null);
        return this;
    }

    public Set<HotelServices> getHotelServices() {
        return this.hotelServices;
    }

    public void setHotelServices(Set<HotelServices> hotelServices) {
        if (this.hotelServices != null) {
            this.hotelServices.forEach(i -> i.setHotel(null));
        }
        if (hotelServices != null) {
            hotelServices.forEach(i -> i.setHotel(this));
        }
        this.hotelServices = hotelServices;
    }

    public Hotel hotelServices(Set<HotelServices> hotelServices) {
        this.setHotelServices(hotelServices);
        return this;
    }

    public Hotel addHotelServices(HotelServices hotelServices) {
        this.hotelServices.add(hotelServices);
        hotelServices.setHotel(this);
        return this;
    }

    public Hotel removeHotelServices(HotelServices hotelServices) {
        this.hotelServices.remove(hotelServices);
        hotelServices.setHotel(null);
        return this;
    }

    public Set<DeliveryRequestPlace> getDeliveryRequestPlaces() {
        return this.deliveryRequestPlaces;
    }

    public void setDeliveryRequestPlaces(Set<DeliveryRequestPlace> deliveryRequestPlaces) {
        if (this.deliveryRequestPlaces != null) {
            this.deliveryRequestPlaces.forEach(i -> i.setHotel(null));
        }
        if (deliveryRequestPlaces != null) {
            deliveryRequestPlaces.forEach(i -> i.setHotel(this));
        }
        this.deliveryRequestPlaces = deliveryRequestPlaces;
    }

    public Hotel deliveryRequestPlaces(Set<DeliveryRequestPlace> deliveryRequestPlaces) {
        this.setDeliveryRequestPlaces(deliveryRequestPlaces);
        return this;
    }

    public Hotel addDeliveryRequestPlace(DeliveryRequestPlace deliveryRequestPlace) {
        this.deliveryRequestPlaces.add(deliveryRequestPlace);
        deliveryRequestPlace.setHotel(this);
        return this;
    }

    public Hotel removeDeliveryRequestPlace(DeliveryRequestPlace deliveryRequestPlace) {
        this.deliveryRequestPlaces.remove(deliveryRequestPlace);
        deliveryRequestPlace.setHotel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return id != null && id.equals(((Hotel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", hotelId=" + getHotelId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", starsNumber=" + getStarsNumber() +
            ", emergencyNumber=" + getEmergencyNumber() +
            "}";
    }
}
