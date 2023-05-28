package com.alok.blogger.blogapp.security;

import com.alok.blogger.blogapp.entities.User;
import com.alok.blogger.blogapp.exceptions.ResourceNotFoundException;
import com.alok.blogger.blogapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email" + username, 0));
        System.out.println("user found");
        return user;
    }
}
