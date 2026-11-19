package com.tecnoshop.api;

public record Libro(
        int id,
        String titulo,
        String autor,
        boolean prestado
) {}