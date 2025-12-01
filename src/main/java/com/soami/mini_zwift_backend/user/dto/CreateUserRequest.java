package com.soami.mini_zwift_backend.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String displayName;
}