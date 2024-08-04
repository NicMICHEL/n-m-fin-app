package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class RatingDTO {

    private Integer id;
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;
    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;
    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;
    @NotBlank(message = "OrderNumber is mandatory")
    @Pattern(regexp = "^[0-9]+(\\[0-9]+)?$", message = "OrderNumber must be an integer")
    private String orderNumber;


    public RatingDTO() {
    }

    public RatingDTO(Integer id, String moodysRating, String sandPRating, String fitchRating, String orderNumber) {
        this.id = id;
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingDTO ratingDTO)) return false;
        return Objects.equals(getId(), ratingDTO.getId()) && Objects.equals(getMoodysRating(), ratingDTO.getMoodysRating()) && Objects.equals(getSandPRating(), ratingDTO.getSandPRating()) && Objects.equals(getFitchRating(), ratingDTO.getFitchRating()) && Objects.equals(getOrderNumber(), ratingDTO.getOrderNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMoodysRating(), getSandPRating(), getFitchRating(), getOrderNumber());
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
                "id=" + id +
                ", moodysRating='" + moodysRating + '\'' +
                ", sandPRating='" + sandPRating + '\'' +
                ", fitchRating='" + fitchRating + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }

}
