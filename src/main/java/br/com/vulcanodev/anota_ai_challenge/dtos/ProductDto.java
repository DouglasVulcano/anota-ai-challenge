package br.com.vulcanodev.anota_ai_challenge.dtos;

public record ProductDto(
        String id,
        String title,
        String ownerId,
        String categoryId,
        Integer price,
        String description) {
}
