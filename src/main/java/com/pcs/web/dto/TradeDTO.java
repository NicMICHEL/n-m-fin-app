package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class TradeDTO {

    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotBlank(message = "BuyQuantity is mandatory")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "BuyQuantity must be a numeric value")
    private String buyQuantity;

    public TradeDTO() {
    }

    public TradeDTO(Integer tradeId, String account, String type, String buyQuantity) {
        this.tradeId = tradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
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

    public String getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(String buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeDTO tradeDTO)) return false;
        return Objects.equals(getTradeId(), tradeDTO.getTradeId()) && Objects.equals(getAccount(), tradeDTO.getAccount()) && Objects.equals(getType(), tradeDTO.getType()) && Objects.equals(getBuyQuantity(), tradeDTO.getBuyQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTradeId(), getAccount(), getType(), getBuyQuantity());
    }

    @Override
    public String toString() {
        return "TradeDTO{" +
                "tradeId=" + tradeId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", buyQuantity='" + buyQuantity + '\'' +
                '}';
    }

}
