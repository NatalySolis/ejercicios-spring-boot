package com.tecnoshop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LibroService {

    public static class LibroPrestadoException extends RuntimeException {
        public LibroPrestadoException(String mensaje) {
            super(mensaje);
        }
    }

    private final List<Libro> libros = new ArrayList<>(List.of(
            new Libro(1, "Clean Code", "Robert C. Martin", false),
            new Libro(2, "Spring Boot en Acción", "Craig Walls", false),
            new Libro(3, "Estructuras de Datos", "Mark Allen Weiss", false),
            new Libro(4, "Arquitectura Limpia", "Robert C. Martin", true),
            new Libro(5, "El Programador Pragmático", "Andrew Hunt", true)
    ));

    public List<Libro> listarTodos() {
        return libros;
    }

    public List<Libro> listarDisponibles() {
        return libros.stream()
                .filter(libro -> !libro.prestado())
                .toList();
    }

    public Optional<Libro> buscarPorId(int id) {
        return libros.stream()
                .filter(libro -> libro.id() == id)
                .findFirst();
    }

    public Map<String, Integer> obtenerEstadisticas() {
        int total = libros.size();
        int prestados = (int) libros.stream().filter(Libro::prestado).count();
        int disponibles = total - prestados;

        return Map.of(
                "total", total,
                "disponibles", disponibles,
                "prestados", prestados
        );
    }

    public Libro crear(Libro nuevoLibro) {
        validarLibro(nuevoLibro, true);
        libros.add(nuevoLibro);
        return nuevoLibro;
    }

    public Optional<Libro> actualizar(int id, Libro datosActualizados) {
        validarLibro(datosActualizados, false);

        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).id() == id) {
                Libro actualizado = new Libro(
                        id,
                        datosActualizados.titulo().trim(),
                        datosActualizados.autor().trim(),
                        datosActualizados.prestado()
                );
                libros.set(i, actualizado);
                return Optional.of(actualizado);
            }
        }
        return Optional.empty();
    }

    public boolean eliminar(int id) {
        Optional<Libro> encontrado = buscarPorId(id);
        if (encontrado.isEmpty()) {
            return false;
        }
        if (encontrado.get().prestado()) {
            throw new LibroPrestadoException("No se puede eliminar un libro que está prestado");
        }
        return libros.remove(encontrado.get());
    }

    private void validarLibro(Libro libro, boolean validarIdDuplicado) {
        if (libro == null || libro.titulo() == null || libro.titulo().isBlank()) {
            throw new IllegalArgumentException("El título del libro es obligatorio");
        }
        if (libro.autor() == null || libro.autor().isBlank()) {
            throw new IllegalArgumentException("El autor del libro es obligatorio");
        }
        if (validarIdDuplicado && buscarPorId(libro.id()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un libro con el id " + libro.id());
        }
    }
}
