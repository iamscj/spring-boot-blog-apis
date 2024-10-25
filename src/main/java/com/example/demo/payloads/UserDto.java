package com.example.demo.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, max = 20, message = "Username must be minimum of 4 characters and maximum of 20 characters")
    private String name;

    @Email(message = "Email address not Valid")
    private String email;

    @NotEmpty
    @Size(min = 8, max = 16, message = "Password must be minimum of 8 characters and maximum of 16 characters")
    private String password;

    @NotEmpty
    private String about;
}
