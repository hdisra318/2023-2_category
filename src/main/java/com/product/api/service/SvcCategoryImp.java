package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

/**
 * Servicio
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.1
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
		
		Category cat = repo.findByCategoryId(category_id);

		if(cat == null)
			throw new ApiException(HttpStatus.BAD_REQUEST, "category does not exist");
		else
			return cat;
			
	}

	@Override
	public ApiResponse createCategory(Category category) {
		
		// Viendo si ya existe la categoria
		Category existingCategory = (Category) repo.findByCategory(category.getCategory());
		
		if(existingCategory != null) {
			
			//Activando la categoria
			if(existingCategory.getStatus() == 0) {

				repo.activateCategory(existingCategory.getCategory_id());
				return new ApiResponse("category has been activated");
			
			} else
				throw new ApiException(HttpStatus.BAD_REQUEST, "category already exists");
			
		}
		
		//Creando la categoria
		repo.createCategory(category.getCategory(), category.getAcronym());
		
		return new ApiResponse("category created");
		
	}

	@Override
	public ApiResponse updateCategory(Integer category_id, Category category) {
		
		// Validacion de la categoria
		Category existingCategory = (Category) repo.findByCategoryId(category_id);
		
		
		if(existingCategory == null) {
			
			throw new ApiException(HttpStatus.BAD_REQUEST, "category does not exist");
		
		} else {
		
			if(existingCategory.getStatus() == 0) {
					
				throw new ApiException(HttpStatus.BAD_REQUEST, "category is not active");
					
			} else {
					
				existingCategory = (Category) repo.findByCategory(category.getCategory());
					
				// Si ya existe esa categoria
				if(existingCategory != null) {
					throw new ApiException(HttpStatus.BAD_REQUEST, "category already exists");
				}
					
				repo.updateCategory(category_id, category.getCategory(), category.getAcronym());
				return new ApiResponse("category updated");
			}				
			
		}
		
		
	}

	@Override
	public ApiResponse deleteCategory(Integer category_id) {
		
		Category existingCategory = (Category) repo.findByCategoryId(category_id);
		
		if(existingCategory == null) {
			
			throw new ApiException(HttpStatus.NOT_FOUND, "category does not exist");
		}
		
		repo.deleteById(category_id);
		
		return new ApiResponse("category removed");
		
	}

}
