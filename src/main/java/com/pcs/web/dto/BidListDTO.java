package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class BidListDTO {

    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotBlank(message = "BidQuantity is mandatory")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "BidQuantity must be a numeric value")
    private String bidQuantity;

    public Integer getBidListId() {
        return bidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(String bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public BidListDTO() {
    }

    public BidListDTO(Integer bidListId, String account, String type, String bidQuantity) {
        this.bidListId = bidListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BidListDTO that)) return false;
        return Objects.equals(getBidListId(), that.getBidListId()) && Objects.equals(getAccount(), that.getAccount()) && Objects.equals(getType(), that.getType()) && Objects.equals(getBidQuantity(), that.getBidQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBidListId(), getAccount(), getType(), getBidQuantity());
    }

    @Override
    public String toString() {
        return "BidListDTO{" +
                "bidListId=" + bidListId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", bidQuantity='" + bidQuantity + '\'' +
                '}';
    }

}
