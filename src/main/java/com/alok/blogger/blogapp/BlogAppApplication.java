package com.alok.blogger.blogapp;

import com.alok.blogger.blogapp.config.AppConstants;
import com.alok.blogger.blogapp.entities.Role;
import com.alok.blogger.blogapp.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
@EntityScan("com.alok.blogger.blogapp")
public class BlogAppApplication implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("ram"));
        try {
            Role role = new Role();
            role.setId(AppConstants.ROLE_ADMIN);
            role.setName("ROLE_ADMIN");
            Role role1 = new Role();
            role1.setId(AppConstants.ROLE_NORMAL);
            role1.setName("ROLE_NORMAL");

            List<Role> roles = List.of(role, role1);
            List<Role> result = this.roleRepo.saveAll(roles);

            result.forEach(r -> System.out.println(r.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


