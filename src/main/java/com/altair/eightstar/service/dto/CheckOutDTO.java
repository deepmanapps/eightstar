package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.altair.eightstar.domain.CheckOut} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckOutDTO implements Serializable {

    private Long id;

    @Lob
    private String roomClearance;

    @Lob
    private String customerReview;

    @Lob
    private String miniBarClearance;

    private Instant lateCheckOut;

    private Boolean isLate;

    private Boolean isCollectedDepositAmount;

    private Double collectedAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomClearance() {
        return roomClearance;
    }

    public void setRoomClearance(String roomClearance) {
        this.roomClearance = roomClearance;
    }

    public String getCustomerReview() {
        return customerReview;
    }

    public void setCustomerReview(String customerReview) {
        this.customerReview = customerReview;
    }

    public String getMiniBarClearance() {
        return miniBarClearance;
    }

    public void setMiniBarClearance(String miniBarClearance) {
        this.miniBarClearance = miniBarClearance;
    }

    public Instant getLateCheckOut() {
        return lateCheckOut;
    }

    public void setLateCheckOut(Instant lateCheckOut) {
        this.lateCheckOut = lateCheckOut;
    }

    public Boolean getIsLate() {
        return isLate;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

    public Boolean getIsCollectedDepositAmount() {
        return isCollectedDepositAmount;
    }

    public void setIsCollectedDepositAmount(Boolean isCollectedDepositAmount) {
        this.isCollectedDepositAmount = isCollectedDepositAmount;
    }

    public Double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckOutDTO)) {
            return false;
        }

        CheckOutDTO checkOutDTO = (CheckOutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkOutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckOutDTO{" +
            "id=" + getId() +
            ", roomClearance='" + getRoomClearance() + "'" +
            ", customerReview='" + getCustomerReview() + "'" +
            ", miniBarClearance='" + getMiniBarClearance() + "'" +
            ", lateCheckOut='" + getLateCheckOut() + "'" +
            ", isLate='" + getIsLate() + "'" +
            ", isCollectedDepositAmount='" + getIsCollectedDepositAmount() + "'" +
            ", collectedAmount=" + getCollectedAmount() +
            "}";
    }
}
