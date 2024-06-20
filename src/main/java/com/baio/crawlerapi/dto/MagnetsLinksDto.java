package com.baio.crawlerapi.dto;

import java.util.List;

public record MagnetsLinksDto(
    String title,
    List<MagnetDto> magnetsLinks
) {
    
}
