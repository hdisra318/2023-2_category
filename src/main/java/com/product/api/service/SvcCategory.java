package com.product.api.service;

import java.util.List;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;

/**
 * Interfaz del Servicio
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.0
 *
 */
public interface SvcCategory {

	/**
	 * Consulta todas las categorias
	 */
	public List<Category> getCategories();
	
	
	/**
	 * Consulta una categoria dado el id 
	 */
	public Category getCategory(Integer category_id);
	
	
	/**
	 * Crea una categoria
	 */
	public ApiResponse createCategory(Category category);
	
	
	/**
	 * Actualiza una categoria
	 */
	public ApiResponse updateCategory(Integer category_id, Category category);
	
	
	/**
	 * Elimina una categoria
	 */
	public ApiResponse deleteCategory(Integer category_id);
	
	
	
}
