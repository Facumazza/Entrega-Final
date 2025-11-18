package com.talento.crud_talento.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talento.crud_talento.modelo.Producto;
import com.talento.crud_talento.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio implements IProductoServicio{

	@Autowired
	private ProductoRepositorio productoRepositorio;

	@Override
	public List<Producto> listarProductos() {
		return productoRepositorio.findAll();
	}

	@Override
	public Producto buscarProductoPorId(Long idProducto) {
		return this.productoRepositorio.findById(idProducto).orElse(null);
	}

	@Override
	public Producto guardarProducto(Producto producto) {
		return this.productoRepositorio.save(producto);
	}

	@Override
	public void borrarProductoPoId(Long idProducto) {
		productoRepositorio.deleteById(idProducto);
	}

}
