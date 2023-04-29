package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.ProductRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductRequestDTO implements Serializable {

    private Long id;

    private String productName;

    private Float productUnitPrice;

    private Float productTotalPrice;

    private Float requestedQuantity;

    private ServiceRequestDTO serviceRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Float productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Float getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Float productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Float getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Float requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public ServiceRequestDTO getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequestDTO serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductRequestDTO)) {
            return false;
        }

        ProductRequestDTO productRequestDTO = (ProductRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductRequestDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productUnitPrice=" + getProductUnitPrice() +
            ", productTotalPrice=" + getProductTotalPrice() +
            ", requestedQuantity=" + getRequestedQuantity() +
            ", serviceRequest=" + getServiceRequest() +
            "}";
    }
}
