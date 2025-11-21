package com.fommo_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public record SpotifyTokenResponseDTO (
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("expires_in")
        int expiresIn
)
{ }
