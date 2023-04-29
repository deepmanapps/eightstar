package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CheckOut.
 */
@Entity
@Table(name = "check_out")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckOut implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "room_clearance")
    private String roomClearance;

    @Lob
    @Column(name = "customer_review")
    private String customerReview;

    @Lob
    @Column(name = "mini_bar_clearance")
    private String miniBarClearance;

    @Column(name = "late_check_out")
    private Instant lateCheckOut;

    @Column(name = "is_late")
    private Boolean isLate;

    @Column(name = "is_collected_deposit_amount")
    private Boolean isCollectedDepositAmount;

    @Column(name = "collected_amount")
    private Double collectedAmount;

    @JsonIgnoreProperties(value = { "checkOut", "serviceRequests", "hotel", "customer" }, allowSetters = true)
    @OneToOne(mappedBy = "checkOut")
    private CheckIn checkIn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CheckOut id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomClearance() {
        return this.roomClearance;
    }

    public CheckOut roomClearance(String roomClearance) {
        this.setRoomClearance(roomClearance);
        return this;
    }

    public void setRoomClearance(String roomClearance) {
        this.roomClearance = roomClearance;
    }

    public String getCustomerReview() {
        return this.customerReview;
    }

    public CheckOut customerReview(String customerReview) {
        this.setCustomerReview(customerReview);
        return this;
    }

    public void setCustomerReview(String customerReview) {
        this.customerReview = customerReview;
    }

    public String getMiniBarClearance() {
        return this.miniBarClearance;
    }

    public CheckOut miniBarClearance(String miniBarClearance) {
        this.setMiniBarClearance(miniBarClearance);
        return this;
    }

    public void setMiniBarClearance(String miniBarClearance) {
        this.miniBarClearance = miniBarClearance;
    }

    public Instant getLateCheckOut() {
        return this.lateCheckOut;
    }

    public CheckOut lateCheckOut(Instant lateCheckOut) {
        this.setLateCheckOut(lateCheckOut);
        return this;
    }

    public void setLateCheckOut(Instant lateCheckOut) {
        this.lateCheckOut = lateCheckOut;
    }

    public Boolean getIsLate() {
        return this.isLate;
    }

    public CheckOut isLate(Boolean isLate) {
        this.setIsLate(isLate);
        return this;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

    public Boolean getIsCollectedDepositAmount() {
        return this.isCollectedDepositAmount;
    }

    public CheckOut isCollectedDepositAmount(Boolean isCollectedDepositAmount) {
        this.setIsCollectedDepositAmount(isCollectedDepositAmount);
        return this;
    }

    public void setIsCollectedDepositAmount(Boolean isCollectedDepositAmount) {
        this.isCollectedDepositAmount = isCollectedDepositAmount;
    }

    public Double getCollectedAmount() {
        return this.collectedAmount;
    }

    public CheckOut collectedAmount(Double collectedAmount) {
        this.setCollectedAmount(collectedAmount);
        return this;
    }

    public void setCollectedAmount(Double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public CheckIn getCheckIn() {
        return this.checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        if (this.checkIn != null) {
            this.checkIn.setCheckOut(null);
        }
        if (checkIn != null) {
            checkIn.setCheckOut(this);
        }
        this.checkIn = checkIn;
    }

    public CheckOut checkIn(CheckIn checkIn) {
        this.setCheckIn(checkIn);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckOut)) {
            return false;
        }
        return id != null && id.equals(((CheckOut) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckOut{" +
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
