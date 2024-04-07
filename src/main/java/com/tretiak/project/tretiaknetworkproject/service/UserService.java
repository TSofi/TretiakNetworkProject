package com.tretiak.project.tretiaknetworkproject.service;

import com.tretiak.project.tretiaknetworkproject.exceptions.NotFoundException;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.RegisterDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.DeleteUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.UpdateUserResponse;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.AuthEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.UserEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.map.MapUser;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.AuthRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.UserRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.GetUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.UpdateUserDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;


    @Autowired
    public UserService(UserRepository userRepository, AuthRepository authRepository) {
        super();
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserEntity addUser(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setLastName(registerDto.getLastName());
        userEntity.setEmail(registerDto.getEmail());

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUsername(registerDto.getUsername());
        authEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        authEntity.setUser(userEntity);

        userEntity.setAuth(authEntity);

        return userRepository.save(userEntity);
    }

    public GetUserDto getUserByUsername(String username) {
        AuthEntity authEntity = authRepository.findByUsername(username).orElseThrow(NotFoundException::user);
        UserEntity userEntity = authEntity.getUser();

        return MapUser.toGetUserDto(userEntity);
    }

    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return users.stream().map(MapUser::toGetUserDto).toList();
    }

    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(NotFoundException::user);

        return MapUser.toGetUserDto(userEntity);
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #id)")
    public DeleteUserDto delete(Long id) {
        if (!userRepository.existsById(id)) {
            log.info("User with given id not found");
            throw NotFoundException.user();
        }
        userRepository.deleteById(id);
        return new DeleteUserDto(id);
    }

    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #id)")
    public UpdateUserResponse update(Long id, UpdateUserDto dto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(NotFoundException::user);

        dto.getfName().ifPresent(userEntity::setFirstName);
        dto.getlName().ifPresent(userEntity::setLastName);
        dto.getEmail().ifPresent(userEntity::setEmail);

        userRepository.save(userEntity);

        return new UpdateUserResponse(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
    }
}