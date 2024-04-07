package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth;

import com.tretiak.project.tretiaknetworkproject.commonTypes.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RegisterDto {

    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "Role is required")
    private UserRole role;

    public RegisterDto(String password, String username, String firstName, String lastName, String email, UserRole role) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public UserRole getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
