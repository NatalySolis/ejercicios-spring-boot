package com.tecnoshop.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController 

public class EjerciciosController {

    public record Categoria(int id, String nombre) {}

    private final List<Categoria> categorias = new ArrayList<>(List.of(
        new Categoria(1, "Laptops"),
        new Categoria(2, "Smartphones"),
        new Categoria(3, "Accesorios")
    ));

    // Ejercicio 1. Tu perfil
    @GetMapping("/api/perfil")
    public Map<String, String> perfil() {
        return Map.of(
            "nombre", "Nataly",
            "ciudad", "Santa Ana",
            "curso", "Full Stack"
        );
    }

    // Ejercicio 2. Sumar
    @GetMapping("/api/sumar")
    public Map<String, Integer> sumar(@RequestParam int a, @RequestParam int b) {
        return Map.of("resultado", a + b);
    }

    // Ejercicio 3. Temperatura (Celsius a Fahrenheit)
    @GetMapping("/api/temperatura/{celsius}")
    public Map<String, Double> temperatura(@PathVariable double celsius) {
        double fahrenheit = (celsius * 9.0 / 5.0) + 32;
        return Map.of(
            "celsius", celsius,
            "fahrenheit", fahrenheit
        );
    }

    // Ejercicio 4. Tabla de multiplicar
    @GetMapping("/api/tabla/{numero}")
    public List<String> tablaMultiplicar(@PathVariable int numero) {
        List<String> tabla = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tabla.add(numero + " x " + i + " = " + (numero * i));
        }
        return tabla;
    }

    // Ejercicio 5. Mayor de edad
    @GetMapping("/api/edad")
    public Map<String, Object> mayorDeEdad(@RequestParam int edad) {
        boolean esMayor = edad >= 18;
        return Map.of(
            "edad", edad,
            "mayorDeEdad", esMayor
        );
    }

    // Ejercicio 6. Listar categorías
    @GetMapping("/api/categorias")
    public List<Categoria> listarCategorias() {
        return categorias;
    }

    // Ejercicio 7. Buscar categoría por ID (devuelve 404 si no existe)
    @GetMapping("/api/categorias/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        for (Categoria categoria : categorias) {
            if (categoria.id() == id) {
                return ResponseEntity.ok(categoria);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Ejercicio 8. Agregar nueva categoría (POST)
    @PostMapping("/api/categorias")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria nuevaCategoria) {
        categorias.add(nuevaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }
}
    

