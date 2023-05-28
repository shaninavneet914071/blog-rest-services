package com.alok.blogger.blogapp.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty(message = "Name must not be left empty")
    @Size(min = 4, max = 20, message = "Name size must be between 4-20 chars")
    private String name;
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "password is null")
    @Size(min = 4, max = 20, message = "Password size must be between 4-20 chars")
    @JsonIgnoreProperties
    private String password;
    @NotNull(message = "about details is null")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
