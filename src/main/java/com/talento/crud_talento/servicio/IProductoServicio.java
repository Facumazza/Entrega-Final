package com.talento.crud_talento.servicio;

import java.util.*;
import com.talento.crud_talento.dtos.ProductoDTO;
public interface IProductoServicio {
	List<ProductoDTO> listarProductos();
	Optional<ProductoDTO> buscarProductoPorId(Long idProducto);
	ProductoDTO actualizarProducto(Long idProducto, ProductoDTO producto);
	ProductoDTO registrarProducto(ProductoDTO productoDTO);
	Optional<ProductoDTO> buscarPorNombre(String nombre);
	void borrarProductoPoId(Long idProducto);
}
