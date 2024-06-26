package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth;
import jakarta.validation.constraints.NotEmpty;
public class Login {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
