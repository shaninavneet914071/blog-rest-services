package com.alok.blogger.blogapp.controllers;

import com.alok.blogger.blogapp.payloads.ApiResponse;
import com.alok.blogger.blogapp.payloads.UserDto;
import com.alok.blogger.blogapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //POST create
    @Autowired
    UserServices userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer id) {
        UserDto updateUser = this.userService.updateUser(userDto, id);
        return ResponseEntity.ok(updateUser);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id) {
        this.userService.deleteUser(id);
//        return  new ResponseEntity(Map.of("message","User deleted successfully"),HttpStatus.OK);
        return new ResponseEntity(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
    }

    //get
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = this.userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        this.userService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }


}
