package com.baio.crawlerapi.dto;

import java.util.List;

public record Page<T>(
        Integer totalPages,
        Integer totalElementsInPage,
        List<T> content
) {
}
