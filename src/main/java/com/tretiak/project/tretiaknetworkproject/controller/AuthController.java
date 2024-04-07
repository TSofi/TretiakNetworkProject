package com.tretiak.project.tretiaknetworkproject.controller;

import com.tretiak.project.tretiaknetworkproject.exceptions.CheckBindingExceptions;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.Login;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.LoginResponseDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.RegisterDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.RegisterResponseDto;
import com.tretiak.project.tretiaknetworkproject.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterDto requestBody) {
//        CheckBindingExceptions.check(bindingResult);
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody Login dto, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        LoginResponseDto responseDto = authService.login(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}