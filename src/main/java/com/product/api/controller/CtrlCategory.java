package com.product.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

/**
 * Controlador
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.1
 *
 */
@RestController
@RequestMapping("/category")
public class CtrlCategory {
	
	/* Objeto de servicio */
	@Autowired
	SvcCategory svc;
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories() throws Exception{
		
		return new ResponseEntity<>(svc.getCategories(), HttpStatus.OK);
	}
	
	
	@GetMapping("/{category_id}")
	public ResponseEntity<Category> getCategory(@PathVariable int category_id){
		
		
		Category cat = svc.getCategory(category_id);
		
		if(cat == null)
			return new ResponseEntity<>(svc.getCategory(category_id), HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(cat, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult){
		
		String message = "";
		
		// Si se encontraron errores en el objeto category
		if(bindingResult.hasErrors()) {
			
			//Obteniendo el mensaje del primer error
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		
		message = svc.createCategory(category);
		
		if(message.equals("category has been activated"))
			return new ResponseEntity<>(message, HttpStatus.OK);
		
		if(message.equals("category already exists"))
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/{category_id}")
	public ResponseEntity<String> updateCategory(@PathVariable int category_id, @Valid @RequestBody Category category, BindingResult bindingResult){
		
		String message = "";
		
		if(bindingResult.hasErrors()) {
			
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		
		message = svc.updateCategory(category_id, category);
		
		if(message.equals("category does not exist") || message.equals("category is not active") ||
				message.equals("category already exists"))
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/{category_id}")
	public ResponseEntity<String> deleteCategory(@PathVariable int category_id){
		
		String message = svc.deleteCategory(category_id);
		
		if(message.equals("category does not exist"))
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
		
	}
}
