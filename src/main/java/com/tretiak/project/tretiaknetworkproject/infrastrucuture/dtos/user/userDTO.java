package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user;

public class userDTO {
    private Long id;
    private String username;

    public userDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public userDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
