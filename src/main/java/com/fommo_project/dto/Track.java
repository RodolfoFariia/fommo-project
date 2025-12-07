package com.fommo_project.dto;

import java.util.List;

public record Track(
        AlbumObj album,
        List<ArtistObj> artists,
        int duration_ms,
        String name,
        int popularity // valor entre 0 e 100, sendo o 100 mais popular
) { }
