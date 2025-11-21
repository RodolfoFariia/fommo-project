package com.fommo_project.controller;

import com.fommo_project.dto.SpotifySearchResponseDTO;
import com.fommo_project.service.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/search")
    public ResponseEntity<SpotifySearchResponseDTO> search(
            @RequestParam("q") String query,
            @RequestParam("type") String type
    ) {
        var result = spotifyService.search(query, type);
        return ResponseEntity.ok(result);
    }
}
