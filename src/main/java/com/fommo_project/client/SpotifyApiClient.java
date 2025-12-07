package com.fommo_project.client;

import com.fommo_project.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyApiClient",
        url = "https://api.spotify.com/v1"
)
public interface SpotifyApiClient {

    @GetMapping("/search")
    SpotifySearchResponseDTO search(
            @RequestHeader("Authorization")
            String token,
            @RequestParam("q")
            String query,
            @RequestParam("type")
            String type
    );

    @GetMapping("/albums/{id}")
    Album getAlbum(
        @RequestHeader("Authorization")
        String token,
        @PathVariable("id")
        String id
    );

    @GetMapping("/tracks/{id}")
    Track getTrack(
            @RequestHeader("Authorization")
            String token,
            @PathVariable("id")
            String id
    );

    @GetMapping("/artists/{id}")
    Artist getArtist(
            @RequestHeader("Authorization")
            String token,
            @PathVariable("id")
            String id
    );
}
