package com.fommo_project.dto;

import java.util.List;

public record SpotifySearchResponseDTO (
        Albums albums,
        Artists artists,
        Tracks tracks
)
{
    // Classes internas para mapear a estrutura aninhada do JSON
    public record Albums(List<Item> items) {}
    public record Artists(List<Item> items) {}
    public record Tracks(List<Item> items) {}

    public record Item(
            String id,
            String name,
            List<Image> images, // Para albuns e artistas
            Album album // Para musicas (para pegar a imagem da capa)
    ) {}

    public record Image(String url, int height, int width) {}
    public record Album(List<Image> images) {}
}
