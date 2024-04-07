package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user;

public class DeleteUserDto {
    private Long id;

    public DeleteUserDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}