package com.fommo_project.controller;

import com.fommo_project.dto.ItemSpotifyResponseDto;
import com.fommo_project.dto.SpotifySearchResponseDTO;
import com.fommo_project.service.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam("type") String type,
            @RequestParam(name="limit", defaultValue = "20")
            int limit,
            @RequestParam(name="offset", defaultValue = "0")
            int offset
    ) {
        var result = spotifyService.search(query, type, limit, offset);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<ItemSpotifyResponseDto> getById(@PathVariable String type, @PathVariable String id){
        ItemSpotifyResponseDto item;

        item = spotifyService.getItemById(type,id);

        return ResponseEntity.ok(item);
    }

    // Dentro da classe SpotifyController

    @GetMapping("/new-releases")
    public ResponseEntity<List<ItemSpotifyResponseDto>> getNewReleases() {
        // 1. Chama o serviço que busca e converte os dados
        List<ItemSpotifyResponseDto> releases = spotifyService.getNewReleases();

        // 2. Retorna HTTP 200 (OK) com a lista no corpo
        return ResponseEntity.ok(releases);
    }

}
