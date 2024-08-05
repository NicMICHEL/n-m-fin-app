package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class RuleNameDTO {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "json is mandatory")
    private String json;
    @NotBlank(message = "Template is mandatory")
    private String template;
    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;


    public RuleNameDTO() {
    }

    public RuleNameDTO(Integer id, String name, String description, String json, String template, String sqlStr,
                       String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleNameDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getJson(), that.getJson()) && Objects.equals(getTemplate(), that.getTemplate()) && Objects.equals(getSqlStr(), that.getSqlStr()) && Objects.equals(getSqlPart(), that.getSqlPart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getJson(), getTemplate(), getSqlStr(), getSqlPart());
    }

    @Override
    public String toString() {
        return "RuleNameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", json='" + json + '\'' +
                ", template='" + template + '\'' +
                ", sqlStr='" + sqlStr + '\'' +
                ", sqlPart='" + sqlPart + '\'' +
                '}';
    }

}
