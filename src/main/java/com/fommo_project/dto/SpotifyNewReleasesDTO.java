package com.fommo_project.dto;

import lombok.Data;

import java.util.List;


public record SpotifyNewReleasesDTO(
        SpotifyAlbumsWrapper albums
) {
    // 2. O Wrapper do meio (o objeto "albums")
    public record SpotifyAlbumsWrapper(
            List<SpotifyAlbumDTO> items
    ) {}

    // 3. O DTO do Álbum (Você provavelmente já tem algo parecido, mas garanta que tenha esses campos)
    public record SpotifyAlbumDTO(
            String id,
            String name,
            String release_date,
            Integer total_tracks,
            List<Image> images,
            List<ArtistObj> artists
    ) {}
}
