package com.altair.eightstar.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.Hotel} entity.
 */
@Schema(description = "The Entity entity.\n@author A true hipster")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelDTO implements Serializable {

    private Long id;

    /**
     * Minimum d'info sur l'hotel
     */
    @Schema(description = "Minimum d'info sur l'hotel")
    private Long hotelId;

    private String name;

    private String description;

    private String adresse;

    private Integer starsNumber;

    private Double emergencyNumber;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(Integer starsNumber) {
        this.starsNumber = starsNumber;
    }

    public Double getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(Double emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelDTO)) {
            return false;
        }

        HotelDTO hotelDTO = (HotelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelDTO{" +
            "id=" + getId() +
            ", hotelId=" + getHotelId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", starsNumber=" + getStarsNumber() +
            ", emergencyNumber=" + getEmergencyNumber() +
            ", location=" + getLocation() +
            "}";
    }
}
