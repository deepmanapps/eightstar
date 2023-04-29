package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ProductRequest.
 */
@Entity
@Table(name = "product_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_unit_price")
    private Float productUnitPrice;

    @Column(name = "product_total_price")
    private Float productTotalPrice;

    @Column(name = "requested_quantity")
    private Float requestedQuantity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parkingAll", "deliveryRequestPlace", "productRequests", "services", "checkIn" }, allowSetters = true)
    private ServiceRequest serviceRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public ProductRequest productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductUnitPrice() {
        return this.productUnitPrice;
    }

    public ProductRequest productUnitPrice(Float productUnitPrice) {
        this.setProductUnitPrice(productUnitPrice);
        return this;
    }

    public void setProductUnitPrice(Float productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Float getProductTotalPrice() {
        return this.productTotalPrice;
    }

    public ProductRequest productTotalPrice(Float productTotalPrice) {
        this.setProductTotalPrice(productTotalPrice);
        return this;
    }

    public void setProductTotalPrice(Float productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Float getRequestedQuantity() {
        return this.requestedQuantity;
    }

    public ProductRequest requestedQuantity(Float requestedQuantity) {
        this.setRequestedQuantity(requestedQuantity);
        return this;
    }

    public void setRequestedQuantity(Float requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public ServiceRequest getServiceRequest() {
        return this.serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public ProductRequest serviceRequest(ServiceRequest serviceRequest) {
        this.setServiceRequest(serviceRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductRequest)) {
            return false;
        }
        return id != null && id.equals(((ProductRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductRequest{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productUnitPrice=" + getProductUnitPrice() +
            ", productTotalPrice=" + getProductTotalPrice() +
            ", requestedQuantity=" + getRequestedQuantity() +
            "}";
    }
}
