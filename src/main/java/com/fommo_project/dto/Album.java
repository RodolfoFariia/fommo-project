package com.fommo_project.dto;

import java.util.List;

public record Album(
        String id,
        int total_tracks,
        List<Image> images,
        String name,
        String release_date,
        List<ArtistObj> artists,
        int popularity
){ }