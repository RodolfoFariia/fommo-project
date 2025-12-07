package com.fommo_project.dto;

import java.util.List;

public record ItemSpotifyResponseDto(
        String type,
        Album album,
        Artist artist,
        Track track
) { }


