package com.talento.crud_talento.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talento.crud_talento.dtos.ProductoDTO;
import com.talento.crud_talento.excepcion.ProductoDuplicadoException;
import com.talento.crud_talento.excepcion.RecursoNoEncontradoExcepcion;
import com.talento.crud_talento.mapper.ProductoMapper;
import com.talento.crud_talento.modelo.Producto;
import com.talento.crud_talento.repositorio.ProductoRepositorio;
@Service
public class ProductoServicio implements IProductoServicio{

	@Autowired
	private ProductoRepositorio productoRepositorio;

	@Autowired
	private ProductoMapper productoMapper;

	@Override
	public List<ProductoDTO> listarProductos() {
		List<Producto> productos = this.productoRepositorio.findAll();
		return productos.stream().map(producto -> productoMapper.toDTO(producto)).toList();
	}

	@Override 
	public Optional<ProductoDTO> buscarPorNombre(String nombre){
		Optional<Producto> producto = productoRepositorio.findByNombre(nombre);
		return producto.map(prod -> productoMapper.toDTO(prod));
	}

	@Override
	public Optional<ProductoDTO> buscarProductoPorId(Long idProducto) {
		Optional<Producto> producto = productoRepositorio.findById(idProducto);
		return producto.map(productoMapper::toDTO);
	}

	@Override
	public ProductoDTO registrarProducto(ProductoDTO productoDTO){

		if (productoDTO.getPrecio() < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}

		if (productoRepositorio.existsByNombre(productoDTO.getNombre())) {
        	throw new ProductoDuplicadoException("El producto ya existe");
    	}

		Producto producto = productoMapper.toEntity(productoDTO);
		Producto productoGuardado = productoRepositorio.save(producto);

		return productoMapper.toDTO(productoGuardado);
	}

	@Override
	public ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO) {

		if (productoDTO.getPrecio() < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}

		Producto productoExistente = productoRepositorio.findById(idProducto)
				.orElseThrow(() -> new RecursoNoEncontradoExcepcion("Producto no encontrado"));

		if (!productoExistente.getNombre().equals(productoDTO.getNombre())
				&& productoRepositorio.existsByNombre(productoDTO.getNombre())) {
			throw new ProductoDuplicadoException("El producto ya existe");
		}

		productoMapper.toEntity(productoDTO, productoExistente);

		Producto productoActualizado = productoRepositorio.save(productoExistente);

		return productoMapper.toDTO(productoActualizado);
	}

	@Override
	public void borrarProductoPoId(Long idProducto) {
		productoRepositorio.deleteById(idProducto);
 	}

}
