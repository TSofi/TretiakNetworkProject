package com.tretiak.project.tretiaknetworkproject.service;

import com.tretiak.project.tretiaknetworkproject.exceptions.AlreadyExistsException;
import com.tretiak.project.tretiaknetworkproject.exceptions.InvalidCredentialsException;
import com.tretiak.project.tretiaknetworkproject.exceptions.NotFoundException;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.*;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.AuthEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.UserEntity;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.AuthRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.repository.UserRepository;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.Login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto){
        if (authRepository.existsByUsername(registerDto.getUsername())) {
            log.info("User with given username already exists");
            throw AlreadyExistsException.userByUsername(registerDto.getUsername());
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            log.info("User with given email already exists");
            throw AlreadyExistsException.userByEmail(registerDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setLastName(registerDto.getLastName());
        userRepository.save(userEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUsername(registerDto.getUsername());
        authEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        authEntity.setRole(registerDto.getRole());
        authEntity.setUser(userEntity);

        authRepository.save(authEntity);
        return new RegisterResponseDto(userEntity.getId(), authEntity.getUsername(), authEntity.getRole());
    }

    public LoginResponseDto login(Login loginDto){
        Optional<AuthEntity> authEntity = authRepository
                .findByUsername(loginDto.getUsername());

        if (authEntity.isEmpty()) {
            log.info("User with given username not found");
            throw NotFoundException.user();
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), authEntity.get().getPassword())) {
            log.info("Invalid data provided");
            throw InvalidCredentialsException.create();
        }

        String token = jwtService.generateToken(authEntity.get());
        return new LoginResponseDto(token);
    }
}
