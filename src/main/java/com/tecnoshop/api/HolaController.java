package com.tecnoshop.api;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class HolaController {
    
    @GetMapping("/api/hola")
    public Map<String, String> hola(){
        return Map.of("mensaje", "Hola desde Sring boot");
    }

    @GetMapping("/api/saludo/{nombre}")
    public Map<String, String> saludo(@PathVariable String nombre){
        return Map.of("mensaje", "Hola," + nombre + "bienvendo");
    }

    @GetMapping ("/api/sumar")
    public Map<String, Double> sumar(@RequestParam double num1, @RequestParam double num2){
        double total= num1+num2;
        return Map.of("suma:", total);
    }

    @GetMapping ("/api/curso")
    public Map<String, String> curso(){
        return Map.of("curso", "Desarrollo Web Full Stack Junior", "estudiante", "Nataly Solis");
    }

}
