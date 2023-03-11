package com.product.api.service;

import java.util.List;

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
	public String createCategory(Category category);
	
	
	/**
	 * Actualiza una categoria
	 */
	public String updateCategory(Integer category_id, Category category);
	
	
	/**
	 * Elimina una categoria
	 */
	public String deleteCategory(Integer category_id);
	
	
	
}
