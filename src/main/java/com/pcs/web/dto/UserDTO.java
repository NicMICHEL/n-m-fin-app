package com.pcs.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class UserDTO {

    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}$",
            message = "Password must contain "
                    + "at least one lowercase letter(a - z),\n"
                    + "at least one uppercase letter(A - Z),\n"
                    + "at least one numeric value(0-9),\n"
                    + "at least one special symbol(!@#$%^&*=+-_),\n"
                    + "and total length should be greater than or equal to 8 and less or equal to 16.")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;


    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String fullname, String role) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
    }

    public UserDTO(Integer id, String username, String password, String fullname, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(getId(), userDTO.getId()) && Objects.equals(getUsername(), userDTO.getUsername()) && Objects.equals(getPassword(), userDTO.getPassword()) && Objects.equals(getFullname(), userDTO.getFullname()) && Objects.equals(getRole(), userDTO.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getFullname(), getRole());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
