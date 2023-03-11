package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;

/**
 * Servicio
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.0
 *
 */
@Service
public class SvcCategoryImp implements SvcCategory {

	
	/** Objeto del repositorio */
	@Autowired
	RepoCategory repo;
	
	
	@Override
	public List<Category> getCategories() {
		
		return repo.findByStatus(1);
	}

	@Override
	public Category getCategory(Integer category_id) {
		
		return repo.findByCategoryId(category_id);
	}

	@Override
	public String createCategory(Category category) {
		
		// Viendo si ya existe la categoria
		Category existingCategory = (Category) repo.findByCategory(category.getCategory());
		
		if(existingCategory != null) {
			
			//Activando la categoria
			if(existingCategory.getStatus() == 0) {
				repo.activateCategory(existingCategory.getCategory_id());
				return "category has been activated";
			} else
				return "category already exists";
			
		}
		
		//Creando la categoria
		repo.createCategory(category.getCategory(), category.getAcronym());
		
		return "category created";
		
	}

	@Override
	public String updateCategory(Integer category_id, Category category) {
		
		// Validacion de la categoria
		Category existingCategory = (Category) repo.findByCategoryId(category_id);
		
		
		if(existingCategory == null) {
			
			return "category does not exist";
		
		} else {
		
			if(existingCategory.getStatus() == 0) {
					
				return "category is not active";
					
			} else {
					
				existingCategory = (Category) repo.findByCategory(category.getCategory());
					
				// Si ya existe esa categoria
				if(existingCategory != null) {
					return "category already exists";
				}
					
				repo.updateCategory(category_id, category.getCategory(), category.getAcronym());
				return "category updated";
			}				
			
		}
		
		
	}

	@Override
	public String deleteCategory(Integer category_id) {
		
		Category existingCategory = (Category) repo.findByCategoryId(category_id);
		
		if(existingCategory == null) {
			
			return "category does not exist";
		}
		
		repo.deleteById(category_id);
		
		return "category removed";
		
	}

}
