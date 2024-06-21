package com.baio.crawlerapi.dto;

import java.util.List;

public record Page<T>(
        Integer page,
        Integer movies,
        List<T> content
) {
}
