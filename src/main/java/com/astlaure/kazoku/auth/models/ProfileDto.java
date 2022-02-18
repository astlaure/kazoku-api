package com.astlaure.kazoku.auth.models;

import com.astlaure.kazoku.users.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;

    private String name;

    private String username;

    private UserRole role;
}
