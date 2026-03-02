package com.talento.crud_talento.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idProducto;

	String nombre;
	String descripcion;
	double precio;
	Integer existencia;
}
