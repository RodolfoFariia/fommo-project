package com.fommo_project.client;

import com.fommo_project.dto.SpotifyTokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Define a url para autenticação
@FeignClient(
        name = "SpotifyAuthClient",
        url = "https://accounts.spotify.com"
)
public interface SpotifyAuthClient {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SpotifyTokenResponseDTO getAccessToken(@RequestBody String body);

}
