package com.securefivewave.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationRequest {
    private String userName;
    private String password;
}
