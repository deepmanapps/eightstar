package com.altair.eightstar.domain;

import com.altair.eightstar.domain.enumeration.RQStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ServiceRequest.
 */
@Entity
@Table(name = "service_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_date")
    private Instant requestDate;

    @Column(name = "request_thru_date")
    private Instant requestThruDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_request")
    private RQStatus statusRequest;

    @Column(name = "rejectt_reason")
    private String rejecttReason;

    @Column(name = "request_description")
    private String requestDescription;

    @Column(name = "object_number")
    private String objectNumber;

    @Column(name = "guest")
    private Boolean guest;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "total_price")
    private Float totalPrice;

    @JsonIgnoreProperties(value = { "location", "serviceRequest", "hotel" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ParkingAll parkingAll;

    @JsonIgnoreProperties(value = { "serviceRequest", "hotel" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private DeliveryRequestPlace deliveryRequestPlace;

    @OneToMany(mappedBy = "serviceRequest")
    @JsonIgnoreProperties(value = { "serviceRequest" }, allowSetters = true)
    private Set<ProductRequest> productRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "childServices", "hotelServices", "parentService", "serviceRequests" }, allowSetters = true)
    private Services services;

    @ManyToOne
    @JsonIgnoreProperties(value = { "checkOut", "serviceRequests", "hotel", "customer" }, allowSetters = true)
    private CheckIn checkIn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return this.requestDate;
    }

    public ServiceRequest requestDate(Instant requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Instant getRequestThruDate() {
        return this.requestThruDate;
    }

    public ServiceRequest requestThruDate(Instant requestThruDate) {
        this.setRequestThruDate(requestThruDate);
        return this;
    }

    public void setRequestThruDate(Instant requestThruDate) {
        this.requestThruDate = requestThruDate;
    }

    public RQStatus getStatusRequest() {
        return this.statusRequest;
    }

    public ServiceRequest statusRequest(RQStatus statusRequest) {
        this.setStatusRequest(statusRequest);
        return this;
    }

    public void setStatusRequest(RQStatus statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getRejecttReason() {
        return this.rejecttReason;
    }

    public ServiceRequest rejecttReason(String rejecttReason) {
        this.setRejecttReason(rejecttReason);
        return this;
    }

    public void setRejecttReason(String rejecttReason) {
        this.rejecttReason = rejecttReason;
    }

    public String getRequestDescription() {
        return this.requestDescription;
    }

    public ServiceRequest requestDescription(String requestDescription) {
        this.setRequestDescription(requestDescription);
        return this;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getObjectNumber() {
        return this.objectNumber;
    }

    public ServiceRequest objectNumber(String objectNumber) {
        this.setObjectNumber(objectNumber);
        return this;
    }

    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }

    public Boolean getGuest() {
        return this.guest;
    }

    public ServiceRequest guest(Boolean guest) {
        this.setGuest(guest);
        return this;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public ServiceRequest quantity(Float quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getTotalPrice() {
        return this.totalPrice;
    }

    public ServiceRequest totalPrice(Float totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ParkingAll getParkingAll() {
        return this.parkingAll;
    }

    public void setParkingAll(ParkingAll parkingAll) {
        this.parkingAll = parkingAll;
    }

    public ServiceRequest parkingAll(ParkingAll parkingAll) {
        this.setParkingAll(parkingAll);
        return this;
    }

    public DeliveryRequestPlace getDeliveryRequestPlace() {
        return this.deliveryRequestPlace;
    }

    public void setDeliveryRequestPlace(DeliveryRequestPlace deliveryRequestPlace) {
        this.deliveryRequestPlace = deliveryRequestPlace;
    }

    public ServiceRequest deliveryRequestPlace(DeliveryRequestPlace deliveryRequestPlace) {
        this.setDeliveryRequestPlace(deliveryRequestPlace);
        return this;
    }

    public Set<ProductRequest> getProductRequests() {
        return this.productRequests;
    }

    public void setProductRequests(Set<ProductRequest> productRequests) {
        if (this.productRequests != null) {
            this.productRequests.forEach(i -> i.setServiceRequest(null));
        }
        if (productRequests != null) {
            productRequests.forEach(i -> i.setServiceRequest(this));
        }
        this.productRequests = productRequests;
    }

    public ServiceRequest productRequests(Set<ProductRequest> productRequests) {
        this.setProductRequests(productRequests);
        return this;
    }

    public ServiceRequest addProductRequest(ProductRequest productRequest) {
        this.productRequests.add(productRequest);
        productRequest.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeProductRequest(ProductRequest productRequest) {
        this.productRequests.remove(productRequest);
        productRequest.setServiceRequest(null);
        return this;
    }

    public Services getServices() {
        return this.services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public ServiceRequest services(Services services) {
        this.setServices(services);
        return this;
    }

    public CheckIn getCheckIn() {
        return this.checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public ServiceRequest checkIn(CheckIn checkIn) {
        this.setCheckIn(checkIn);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRequest)) {
            return false;
        }
        return id != null && id.equals(((ServiceRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceRequest{" +
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
            "}";
    }
}
