package com.pramaanikaran.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppUserDTO {
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    private String password;
}
