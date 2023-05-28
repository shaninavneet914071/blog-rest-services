package com.alok.blogger.blogapp.services;

import com.alok.blogger.blogapp.payloads.UserDto;

import java.util.List;

public interface UserServices {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer id);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

    void deleteAllUsers();

    UserDto registerNewUser(UserDto userDto);
}
