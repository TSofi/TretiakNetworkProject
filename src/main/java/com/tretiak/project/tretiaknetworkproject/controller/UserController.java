package com.tretiak.project.tretiaknetworkproject.controller;

import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.auth.RegisterDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.DeleteUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.GetUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.UpdateUserDto;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user.UpdateUserResponse;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.UserEntity;
import com.tretiak.project.tretiaknetworkproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetUserDto> getMe(Principal principal) {
        String username = principal.getName();
        GetUserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<GetUserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetUserDto getUser(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteUserDto> delete(@PathVariable Long id) {
        DeleteUserDto dto =  userService.delete(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UpdateUserResponse> update(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        UpdateUserResponse responseDto = userService.update(id, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // for adding a new user:
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> addUser(@RequestBody RegisterDto registerDto) {
        UserEntity userEntity = userService.addUser(registerDto);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

}
