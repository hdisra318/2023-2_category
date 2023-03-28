package com.product.api.service;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.entity.Product;
import com.product.api.repository.RepoCategory;
import com.product.api.repository.RepoProduct;
import com.product.exception.ApiException;

@Service
public class SvcProductImp implements SvcProduct {

	@Autowired
	RepoProduct repo;
	
	@Autowired
	RepoCategory repoCategory;

	@Override
	public Product getProduct(String gtin) {
		
		Product product = repo.findByGtinAndStatus(gtin, 1);
		if (product != null) {
			product.setCategory(repoCategory.findByCategoryId(product.getCategory_id()));
			return product;
		}else
			throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
		
	}

	
	
	/*
	 * 4. Implementar el método createProduct considerando las siguientes validaciones:
  		1. validar que la categoría del nuevo producto exista
  		2. el código GTIN y el nombre del producto son únicos
  		3. si al intentar realizar un nuevo registro ya existe un producto con el mismo GTIN pero tiene estatus 0, 
  		   entonces se debe cambiar el estatus del producto existente a 1 y actualizar sus datos con los del nuevo registro
	 */
	@Override
	public ApiResponse createProduct(Product in) {
		
		Category existingCategory = (Category) repoCategory.findByCategoryId(in.getCategory_id());
		
		// Verificando si ya existe la categoria del producto
		if(existingCategory != null) { 
			
			in.setStatus(1);
			
			try {
				
				//Intentando guardar en la base de datos
				repo.save(in);
				
			} catch(DataIntegrityViolationException e) {
			
				//Producto con status 0
				Product existingProduct0 = repo.findByGtinAndStatus(in.getGtin(), 0);
				
				//Produtco con staus 1
				Product existingProduct1 = repo.findByGtinAndStatus(in.getGtin(), 1);
				
				// Si hay un producto con el mismo gtin pero con status 0
				if(e.getLocalizedMessage().contains("gtin") && existingProduct0 != null) {
					
					repo.activateProduct(existingProduct0.getGtin());
					repo.updateProduct(in.getProduct_id(), in.getGtin(), in.getProduct(), in.getDescription(), 
							in.getPrice(), in.getStock(), in.getCategory_id());
					
					return new ApiResponse("product activated"); 
				}
				
				// Si hay un producto con el mismo gtin pero con status 1
				if(e.getLocalizedMessage().contains("gtin") && existingProduct1 != null) {
					
					throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
				}
				
				// Si hay un producto con el mismo gtin pero con el mismo nombre
				if(e.getLocalizedMessage().contains("product")) {
					
					throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exist");
				} 
				
				if (e.contains(SQLIntegrityConstraintViolationException.class))
					throw new ApiException(HttpStatus.BAD_REQUEST, "category not found");
				
				
			}
				
			
		} else {
			
			throw new ApiException(HttpStatus.NOT_FOUND, "category not found");
		}
		
		
		return new ApiResponse("product created");
	}

	
	
	@Override
	public ApiResponse updateProduct(Product in, Integer id) {
		Integer updated = 0;
		try {
			updated = repo.updateProduct(id, in.getGtin(), in.getProduct(), in.getDescription(), in.getPrice(), in.getStock(), in.getCategory_id());
		}catch (DataIntegrityViolationException e) {
			if (e.getLocalizedMessage().contains("gtin"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
			if (e.getLocalizedMessage().contains("product"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exist");
			if (e.contains(SQLIntegrityConstraintViolationException.class))
				throw new ApiException(HttpStatus.BAD_REQUEST, "category not found");
		}
		if(updated == 0)
			throw new ApiException(HttpStatus.BAD_REQUEST, "product cannot be updated");
		else
			return new ApiResponse("product updated");
	}

	@Override
	public ApiResponse deleteProduct(Integer id) {
		if (repo.deleteProduct(id) > 0)
			return new ApiResponse("product removed");
		else
			throw new ApiException(HttpStatus.BAD_REQUEST, "product cannot be deleted");
	}

	@Override
	public ApiResponse updateProductStock(String gtin, Integer stock) {
		Product product = getProduct(gtin);
		if(stock > product.getStock())
			throw new ApiException(HttpStatus.BAD_REQUEST, "stock to update is invalid");
		
		repo.updateProductStock(gtin, product.getStock() - stock);
		return new ApiResponse("product stock updated");
	}
}
