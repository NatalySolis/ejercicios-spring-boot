package com.tecnoshop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service 
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>(List.of (
        new Producto(1, "Audífonos inálambricos", 49.99, 5),
        new Producto(2, "Mouse inalámbrico", 18.50, 10),
        new Producto(3, "Teclado mecánico", 59.90, 0)
    ));
    private int siguienteId = 4;

    public List<Producto> listar(){
        return productos;
    }

    public Optional<Producto> buscar(int id){
        return productos.stream()
        .filter(p -> p.id() == id)
        .findFirst();
    }

    public Producto crear(Producto datos){
        validar(datos);
        Producto nuevo = new Producto(siguienteId++, datos.nombre().trim(), datos.precio(),
        datos.stock());
        productos.add(nuevo);
        return nuevo;
    }

    public Optional<Producto> actualizar(int id, Producto datos){
        validar(datos);
        for (int i = 0; i < productos.size(); i++){
            if(productos.get(i).id() == id){
                Producto cambiado = new Producto(id, datos.nombre().trim(), datos.precio(),
                datos.stock());
                productos.set(i, cambiado);
                return Optional.of(cambiado);
            }
        }
        return Optional.empty();
    }

    public boolean eliminar(int id){
        return productos.removeIf(p -> p.id() == id);
    }

    private void validar(Producto datos){
        if(datos.nombre() == null || datos.nombre().isBlank()){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if(datos.precio() <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if(datos.stock() < 0){
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
    
}
