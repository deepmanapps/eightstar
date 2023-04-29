package com.altair.eightstar.domain;

import com.altair.eightstar.domain.enumeration.PaymentMethod;
import com.altair.eightstar.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CheckIn.
 */
@Entity
@Table(name = "check_in")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identity_path")
    private String identityPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "arrival_date")
    private Instant arrivalDate;

    @Column(name = "departure_date")
    private Instant departureDate;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "smooking")
    private Boolean smooking;

    @Column(name = "adults")
    private Integer adults;

    @Column(name = "children")
    private Integer children;

    @Column(name = "notes")
    private String notes;

    @JsonIgnoreProperties(value = { "checkIn" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CheckOut checkOut;

    @OneToMany(mappedBy = "checkIn")
    @JsonIgnoreProperties(value = { "parkingAll", "deliveryRequestPlace", "productRequests", "services", "checkIn" }, allowSetters = true)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "checkIns", "parkingAlls", "hotelServices", "deliveryRequestPlaces" }, allowSetters = true)
    private Hotel hotel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "checkIns" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CheckIn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityPath() {
        return this.identityPath;
    }

    public CheckIn identityPath(String identityPath) {
        this.setIdentityPath(identityPath);
        return this;
    }

    public void setIdentityPath(String identityPath) {
        this.identityPath = identityPath;
    }

    public Status getStatus() {
        return this.status;
    }

    public CheckIn status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getDepositAmount() {
        return this.depositAmount;
    }

    public CheckIn depositAmount(Double depositAmount) {
        this.setDepositAmount(depositAmount);
        return this;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public CheckIn paymentMethod(PaymentMethod paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Instant getArrivalDate() {
        return this.arrivalDate;
    }

    public CheckIn arrivalDate(Instant arrivalDate) {
        this.setArrivalDate(arrivalDate);
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getDepartureDate() {
        return this.departureDate;
    }

    public CheckIn departureDate(Instant departureDate) {
        this.setDepartureDate(departureDate);
        return this;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public CheckIn roomType(String roomType) {
        this.setRoomType(roomType);
        return this;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Boolean getSmooking() {
        return this.smooking;
    }

    public CheckIn smooking(Boolean smooking) {
        this.setSmooking(smooking);
        return this;
    }

    public void setSmooking(Boolean smooking) {
        this.smooking = smooking;
    }

    public Integer getAdults() {
        return this.adults;
    }

    public CheckIn adults(Integer adults) {
        this.setAdults(adults);
        return this;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return this.children;
    }

    public CheckIn children(Integer children) {
        this.setChildren(children);
        return this;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getNotes() {
        return this.notes;
    }

    public CheckIn notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CheckOut getCheckOut() {
        return this.checkOut;
    }

    public void setCheckOut(CheckOut checkOut) {
        this.checkOut = checkOut;
    }

    public CheckIn checkOut(CheckOut checkOut) {
        this.setCheckOut(checkOut);
        return this;
    }

    public Set<ServiceRequest> getServiceRequests() {
        return this.serviceRequests;
    }

    public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
        if (this.serviceRequests != null) {
            this.serviceRequests.forEach(i -> i.setCheckIn(null));
        }
        if (serviceRequests != null) {
            serviceRequests.forEach(i -> i.setCheckIn(this));
        }
        this.serviceRequests = serviceRequests;
    }

    public CheckIn serviceRequests(Set<ServiceRequest> serviceRequests) {
        this.setServiceRequests(serviceRequests);
        return this;
    }

    public CheckIn addServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.add(serviceRequest);
        serviceRequest.setCheckIn(this);
        return this;
    }

    public CheckIn removeServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.remove(serviceRequest);
        serviceRequest.setCheckIn(null);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public CheckIn hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CheckIn customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckIn)) {
            return false;
        }
        return id != null && id.equals(((CheckIn) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckIn{" +
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
            "}";
    }
}
