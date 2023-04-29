package com.altair.eightstar.service.dto;

import com.altair.eightstar.domain.enumeration.RQStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.ServiceRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceRequestDTO implements Serializable {

    private Long id;

    private Instant requestDate;

    private Instant requestThruDate;

    private RQStatus statusRequest;

    private String rejecttReason;

    private String requestDescription;

    private String objectNumber;

    private Boolean guest;

    private Float quantity;

    private Float totalPrice;

    private ParkingAllDTO parkingAll;

    private DeliveryRequestPlaceDTO deliveryRequestPlace;

    private ServicesDTO services;

    private CheckInDTO checkIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Instant getRequestThruDate() {
        return requestThruDate;
    }

    public void setRequestThruDate(Instant requestThruDate) {
        this.requestThruDate = requestThruDate;
    }

    public RQStatus getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(RQStatus statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getRejecttReason() {
        return rejecttReason;
    }

    public void setRejecttReason(String rejecttReason) {
        this.rejecttReason = rejecttReason;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getObjectNumber() {
        return objectNumber;
    }

    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }

    public Boolean getGuest() {
        return guest;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ParkingAllDTO getParkingAll() {
        return parkingAll;
    }

    public void setParkingAll(ParkingAllDTO parkingAll) {
        this.parkingAll = parkingAll;
    }

    public DeliveryRequestPlaceDTO getDeliveryRequestPlace() {
        return deliveryRequestPlace;
    }

    public void setDeliveryRequestPlace(DeliveryRequestPlaceDTO deliveryRequestPlace) {
        this.deliveryRequestPlace = deliveryRequestPlace;
    }

    public ServicesDTO getServices() {
        return services;
    }

    public void setServices(ServicesDTO services) {
        this.services = services;
    }

    public CheckInDTO getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckInDTO checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRequestDTO)) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id=" + getId() +
            ", requestDate='" + getRequestDate() + "'" +
            ", requestThruDate='" + getRequestThruDate() + "'" +
            ", statusRequest='" + getStatusRequest() + "'" +
            ", rejecttReason='" + getRejecttReason() + "'" +
            ", requestDescription='" + getRequestDescription() + "'" +
            ", objectNumber='" + getObjectNumber() + "'" +
            ", guest='" + getGuest() + "'" +
            ", quantity=" + getQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", parkingAll=" + getParkingAll() +
            ", deliveryRequestPlace=" + getDeliveryRequestPlace() +
            ", services=" + getServices() +
            ", checkIn=" + getCheckIn() +
            "}";
    }
}
