package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class CurvePointDTO {

    private Integer id;

    @NotBlank(message = "CurveId is mandatory")
    @Pattern(regexp = "^[0-9]+(\\[0-9]+)?$", message = "CurveId must be an integer")
    private String curveId;

    @NotBlank(message = "Term is mandatory")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Term must be a numeric value")
    private String term;

    @NotBlank(message = "Value is mandatory")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Value must be a numeric value")
    private String value;

    public CurvePointDTO() {
    }

    public CurvePointDTO(Integer id, String curveId, String term, String value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurveId() {
        return curveId;
    }

    public void setCurveId(String curveId) {
        this.curveId = curveId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurvePointDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCurveId(), that.getCurveId()) && Objects.equals(getTerm(), that.getTerm()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCurveId(), getTerm(), getValue());
    }

    @Override
    public String toString() {
        return "CurvePointDTO{" +
                "id=" + id +
                ", curveId='" + curveId + '\'' +
                ", term='" + term + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
