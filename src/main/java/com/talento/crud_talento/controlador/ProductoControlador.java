package com.talento.crud_talento.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talento.crud_talento.dtos.ProductoDTO;
import com.talento.crud_talento.excepcion.RecursoNoEncontradoExcepcion;
import com.talento.crud_talento.servicio.ProductoServicio;



@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoControlador {
	private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

	@Autowired
	private ProductoServicio productoServicio;

	@GetMapping("/productos")
	public List<ProductoDTO> obtenerProductos(){
		List<ProductoDTO> productosDTOs = this.productoServicio.listarProductos();
		logger.info("Productos obtenidos: ");
		productosDTOs.forEach(producto -> logger.info(producto.toString()));
		return productosDTOs;
	}

	@GetMapping("/buscar/nombre/{nombre}")
	public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
		Optional<ProductoDTO> productoDTO = this.productoServicio.buscarPorNombre(nombre);
		return productoDTO.isPresent() ? ResponseEntity.ok(productoDTO.get())
		 : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto con nombre: " + nombre);
	}

	@GetMapping("/productos/{id}")
	public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {

    ProductoDTO productoDTO = productoServicio.buscarProductoPorId(id)
            .orElseThrow(() -> 
                new RecursoNoEncontradoExcepcion("No se encontró el id: " + id));

    return ResponseEntity.ok(productoDTO);
}

	@PostMapping("/registrar-productos")
	public ResponseEntity<ProductoDTO> registrarProducto(
			@RequestBody ProductoDTO productoDTO
	){
		ProductoDTO productoRegistrado =
				this.productoServicio.registrarProducto(productoDTO);

		return ResponseEntity.status(HttpStatus.CREATED)
							.body(productoRegistrado);
	}

	@PutMapping("/productos/{id}")
	public ResponseEntity<ProductoDTO> actualizarProducto(
			@PathVariable Long id,
			@RequestBody ProductoDTO productoDTO
	) {
		ProductoDTO productoActualizado =
				this.productoServicio.actualizarProducto(id, productoDTO);

		return ResponseEntity.ok(productoActualizado);
	}

	@DeleteMapping("/productos/{id}")
	public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Long id) {

    ProductoDTO productoDTO = productoServicio.buscarProductoPorId(id)
            .orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("No se encontro el id " + id));

    productoServicio.borrarProductoPoId(id);

    Map<String, Boolean> respuesta = new HashMap<>();
    respuesta.put("eliminado", true);

    return ResponseEntity.ok(respuesta);
}

}





