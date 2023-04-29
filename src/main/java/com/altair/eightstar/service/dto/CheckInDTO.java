package com.altair.eightstar.service.dto;

import com.altair.eightstar.domain.enumeration.PaymentMethod;
import com.altair.eightstar.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.CheckIn} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckInDTO implements Serializable {

    private Long id;

    private String identityPath;

    private Status status;

    private Double depositAmount;

    private PaymentMethod paymentMethod;

    private Instant arrivalDate;

    private Instant departureDate;

    private String roomType;

    private Boolean smooking;

    private Integer adults;

    private Integer children;

    private String notes;

    private CheckOutDTO checkOut;

    private HotelDTO hotel;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityPath() {
        return identityPath;
    }

    public void setIdentityPath(String identityPath) {
        this.identityPath = identityPath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Boolean getSmooking() {
        return smooking;
    }

    public void setSmooking(Boolean smooking) {
        this.smooking = smooking;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CheckOutDTO getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(CheckOutDTO checkOut) {
        this.checkOut = checkOut;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckInDTO)) {
            return false;
        }

        CheckInDTO checkInDTO = (CheckInDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkInDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckInDTO{" +
            "id=" + getId() +
            ", identityPath='" + getIdentityPath() + "'" +
            ", status='" + getStatus() + "'" +
            ", depositAmount=" + getDepositAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", roomType='" + getRoomType() + "'" +
            ", smooking='" + getSmooking() + "'" +
            ", adults=" + getAdults() +
            ", children=" + getChildren() +
            ", notes='" + getNotes() + "'" +
            ", checkOut=" + getCheckOut() +
            ", hotel=" + getHotel() +
            ", customer=" + getCustomer() +
            "}";
    }
}
