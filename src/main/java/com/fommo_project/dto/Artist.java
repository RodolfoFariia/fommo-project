package com.fommo_project.dto;

import java.util.List;

public record Artist(
        Followers followers,
        List<String> genres,
        List<Image> images,
        String name,
        int popularity
){ }
