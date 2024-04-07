package com.tretiak.project.tretiaknetworkproject.infrastrucuture.map;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.GetUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.UserEntity;

public class MapUser {
    public static GetUserDto toGetUserDto(UserEntity userEntity) {
        return new GetUserDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }
}
