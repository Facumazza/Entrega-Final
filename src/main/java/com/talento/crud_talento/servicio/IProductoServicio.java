package com.talento.crud_talento.servicio;

import java.util.*;

import com.talento.crud_talento.modelo.Producto;

public interface IProductoServicio {
	List<Producto> listarProductos();
	Producto buscarProductoPorId(Long idProducto);
	Producto guardarProducto(Producto producto);
	void borrarProductoPoId(Long idProducto);
}
