package com.fommo_project.dto;

import java.util.List;

// Obj de album que vem na música
public record AlbumObj(
        int total_tracks,
        List<Image> images,
        String name,
        String release_date
) { }
