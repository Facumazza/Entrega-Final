package com.talento.crud_talento.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talento.crud_talento.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

    java.util.Optional<Producto> findByNombre(String nombre);
    
}
