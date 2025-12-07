package com.fommo_project.controller;

import com.fommo_project.dto.ItemSpotifyResponseDto;
import com.fommo_project.dto.SpotifySearchResponseDTO;
import com.fommo_project.service.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{type}/{id}")
    public ResponseEntity<ItemSpotifyResponseDto> getById(@PathVariable String type, @PathVariable String id){
        ItemSpotifyResponseDto item;

        item = spotifyService.getItemById(type,id);

        return ResponseEntity.ok(item);
    }
}
