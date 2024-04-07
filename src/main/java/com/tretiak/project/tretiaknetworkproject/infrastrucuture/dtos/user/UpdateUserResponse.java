package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user;

public class UpdateUserResponse {
    private Long id;
    private String fName;
    private String lName;
    private String email;

    public UpdateUserResponse(Long id, String fName, String lName, String email) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
