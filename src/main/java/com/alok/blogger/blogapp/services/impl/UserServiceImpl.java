package com.alok.blogger.blogapp.services.impl;

import com.alok.blogger.blogapp.config.AppConstants;
import com.alok.blogger.blogapp.entities.Role;
import com.alok.blogger.blogapp.entities.User;
import com.alok.blogger.blogapp.exceptions.ResourceNotFoundException;
import com.alok.blogger.blogapp.payloads.UserDto;
import com.alok.blogger.blogapp.repository.RoleRepo;
import com.alok.blogger.blogapp.repository.UserRepo;
import com.alok.blogger.blogapp.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    /**
     * @param userDto
     * @param id
     * @return
     */
    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserDto userDto = this.userToDto(user);
        return userDto;
    }

    /**
     * @return
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDto;
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public void deleteAllUsers() {

        this.userRepo.deleteAll();
    }


    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
//user.setName(userDto.getName());
//    user.setId(userDto.getId());
//    user.setEmail(userDto.getEmail());
//    user.setPassword(userDto.getPassword());
//    user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }

    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(AppConstants.ROLE_NORMAL).get();
        user.getRoles().add(role);
        User user1 = this.userRepo.save(user);
        return this.modelMapper.map(user1, UserDto.class);
    }

}
