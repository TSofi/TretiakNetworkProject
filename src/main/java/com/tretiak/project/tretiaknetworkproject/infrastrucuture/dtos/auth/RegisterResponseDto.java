package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth;

import com.tretiak.project.tretiaknetworkproject.commonTypes.UserRole;
import lombok.Getter;
import lombok.Setter;

public class RegisterResponseDto {
    private Long uid;
    @Setter
    @Getter
    private String username;
    private UserRole role;

    public RegisterResponseDto(Long uid, String username, UserRole role) {
        this.uid = uid;
        this.username = username;
        this.role = role;
    }

}